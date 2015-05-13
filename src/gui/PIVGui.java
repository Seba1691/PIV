package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import manager.FiltersManager;
import manager.ManagerException;
import pivLayer.ElementoProcesable;
import pivLayer.Filtro;
import pivLayer.FiltroPIV;
import pivLayer.FiltroPostProcesamiento;
import pivLayer.FiltroPreProcesamiento;
import pivLayer.FiltroVisualizacion;
import pivLayer.Imagen;
import pivLayer.MapaVectores;
import pivLayer.PostProcesador;
import pivLayer.PreProcesador;
import pivLayer.Procesador;
import pivLayer.ProcesadorPIV;
import pivLayer.Seleccionador;
import pivLayer.SeleccionadorCascada;
import pivLayer.SeleccionadorPares;
import utiles.FileHandling;
import wapper.WrapperException;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PIVGui {

	private static JFrame PIV;

	private DefaultListModel<File> selectedImagesFilesModel;
	private JList<File> listImagesFiles;
	private List<Imagen> imagesList;

	private DefaultListModel<File> selectedVectorFilesModel;
	private JList<File> listVectorFiles;
	private List<MapaVectores> vectorList;

	private DefaultComboBoxModel<ComboItemFilter> preProcessingFiltersModel;
	private DefaultComboBoxModel<ComboItemFilter> postProcessingFiltersModel;
	private DefaultComboBoxModel<ComboItemFilter> pivProcessingFiltersModel;
	private DefaultComboBoxModel<ComboItemFilter> visualizationFiltersModel;

	private JPanel gridPreProcessingPanel;
	private JPanel gridPIVProcessingPanel;
	private JPanel gridPostProcessingPanel;
	private JPanel gridVisualizationPanel;
	private JComboBox<ComboItemFilter> comboBoxPreProcessing;
	private JComboBox<ComboItemFilter> comboBoxPIVProcessing;
	private JComboBox<ComboItemFilter> comboBoxPostProcessing;
	private JComboBox<ComboItemFilter> comboBoxVisualization;

	private List<FiltroPreProcesamiento> preProcessingFilterList;
	private List<FiltroPIV> pivProcessingFilterList;
	private List<FiltroPostProcesamiento> postProcessingFilterList;
	private List<FiltroVisualizacion> visualizationFilterList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PIVGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws ManagerException
	 */
	public PIVGui() throws ManagerException {
		initVariables();
		initialize();
		loadFilters();
		PIV.setVisible(true);
	}

	private void initVariables() {
		preProcessingFilterList = new ArrayList<>();
		pivProcessingFilterList = new ArrayList<>();
		postProcessingFilterList = new ArrayList<>();
		visualizationFilterList = new ArrayList<>();
	}

	public void addSelectedFile(File f) {
		try {
			if (FileHandling.getExtension(f).equals("jvc")) {
				this.selectedVectorFilesModel.addElement(f);
				vectorList.add(new MapaVectores(FileHandling.readArrayFromFile(f.getPath())));
			} else {
				this.selectedImagesFilesModel.addElement(f);
				imagesList.add(new Imagen(FileHandling.getBufferedImage(f)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
		} catch (UnsupportedLookAndFeelException | ParseException e) {
			e.printStackTrace();
		}

		PIV = new JFrame();
		PIV.setTitle("PIV Viewver");
		PIV.setBounds(100, 100, 809, 495);
		PIV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PIV.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		PIV.getContentPane().add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPaneFiles = new JSplitPane();
		splitPaneFiles.setOrientation(JSplitPane.VERTICAL_SPLIT);

		// -- MENU --//

		JToolBar toolBar = new JToolBar();
		PIV.getContentPane().add(toolBar, BorderLayout.NORTH);
		toolBar.setRollover(true);
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		toolBar.setFloatable(false);
		JMenuBar menuBar = new JMenuBar();
		toolBar.add(menuBar);

		JMenu menuFile = new JMenu("Archivo");
		menuBar.add(menuFile);

		JMenuItem itemChooseFile = new JMenuItem("Seleccionar..");
		itemChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				int returnVal = fc.showOpenDialog(PIV);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File[] files = fc.getSelectedFiles();
					for (File f : files) {
						addSelectedFile(f);
					}
				} else {
					System.out.println("Open command cancelled by user.");
				}

			}
		});
		menuFile.add(itemChooseFile);
		
		JMenu menuPreProcesamiento = new JMenu("Pre Procesamiento");
		menuBar.add(menuPreProcesamiento);
		
		JMenuItem itemPreview = new JMenuItem("Previsualizar");
		itemPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doPreview();
			}
		});
		menuPreProcesamiento.add(itemPreview);

		JMenu menuPIV = new JMenu("PIV");
		menuBar.add(menuPIV);

		JMenuItem itemDoPIV = new JMenuItem("Ejecutar PIV");
		itemDoPIV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<ElementoProcesable> results = doPIV();
				visualizar(results);
				guardar(results);
			}
		});
		menuPIV.add(itemDoPIV);

		JMenu menuPreferences = new JMenu("Preferencias");
		menuBar.add(menuPreferences);

		JMenuItem itemConfig = new JMenuItem("Configuracion");
		itemConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingsFrame sf = new SettingsFrame();
				sf.setVisible(true);
			}
		});
		menuPreferences.add(itemConfig);

		// -- Archivos --//

		// -- Imagenes --//
		selectedImagesFilesModel = new DefaultListModel<File>();
		imagesList = new ArrayList<Imagen>();
		JTabbedPane tabbedPaneImagesFiles = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneImagesFiles.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		splitPaneFiles.setLeftComponent(tabbedPaneImagesFiles);
		splitPane.setLeftComponent(splitPaneFiles);

		JScrollPane scrollPaneFiles = new JScrollPane();
		tabbedPaneImagesFiles.addTab("Imagenes", null, scrollPaneFiles, null);

		listImagesFiles = new JList<File>();
		listImagesFiles.setModel(selectedImagesFilesModel);
		listImagesFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {					
					ImageFrame f = new ImageFrame(imagesList.get(listImagesFiles.getSelectedIndex()));
					f.setVisible(true);
				}
			}
		});
		scrollPaneFiles.setViewportView(listImagesFiles);

		// -- Mapa de vectores --//

		selectedVectorFilesModel = new DefaultListModel<File>();
		vectorList = new ArrayList<MapaVectores>();
		JTabbedPane tabbedPaneVectorFiles = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneVectorFiles.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		splitPaneFiles.setRightComponent(tabbedPaneVectorFiles);

		JScrollPane scrollPaneVectorFiles = new JScrollPane();
		tabbedPaneVectorFiles.addTab("Resultados", null, scrollPaneVectorFiles, null);

		listVectorFiles = new JList<File>();
		listVectorFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					List<ElementoProcesable> list = new ArrayList<ElementoProcesable>();
					list.add(vectorList.get(listVectorFiles.getSelectedIndex()));
					visualizar(list);
				}
			}
		});
		listVectorFiles.setModel(selectedVectorFilesModel);
		scrollPaneVectorFiles.setViewportView(listVectorFiles);

		// -- Procesamiento --//
		JTabbedPane tabbedPaneProcessing = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPaneProcessing);

		// ----- PRE PROCESAMIENTO PANEL
		JPanel panelPreProcessing = new JPanel();
		tabbedPaneProcessing.addTab("Pre-Procesamiento", null, panelPreProcessing, null);
		preProcessingFiltersModel = new DefaultComboBoxModel<ComboItemFilter>();
		panelPreProcessing.setLayout(new BorderLayout(0, 0));

		JPanel panelAddPreProcessingFilters = new JPanel();
		FlowLayout fl_panelAddPreProcessingFilters = (FlowLayout) panelAddPreProcessingFilters.getLayout();
		fl_panelAddPreProcessingFilters.setAlignOnBaseline(true);
		fl_panelAddPreProcessingFilters.setAlignment(FlowLayout.LEFT);
		panelPreProcessing.add(panelAddPreProcessingFilters, BorderLayout.NORTH);

		comboBoxPreProcessing = new JComboBox<ComboItemFilter>();
		panelAddPreProcessingFilters.add(comboBoxPreProcessing);
		comboBoxPreProcessing.setModel(preProcessingFiltersModel);

		JButton btnAddPreProcessingFilter = new JButton("Agregar");
		btnAddPreProcessingFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filterName = ((ComboItemFilter) comboBoxPreProcessing.getSelectedItem()).getFilterName();
				String filterClass = ((ComboItemFilter) comboBoxPreProcessing.getSelectedItem()).getFilterClassName();
				FiltroPreProcesamiento filtroPreProcesamiento;
				try {
					URLClassLoader filtersClassLoader = FiltersManager.getInstance().getFiltersClassLoader();
					filtroPreProcesamiento = ((FiltroPreProcesamiento) Class.forName(filterClass, true, filtersClassLoader).newInstance());
					FilterRowPanel<FiltroPreProcesamiento> newRowPanel = new FilterRowPanel<FiltroPreProcesamiento>(filtroPreProcesamiento, filterName, preProcessingFilterList);
					newRowPanel.insertRowIn(gridPreProcessingPanel);
					preProcessingFilterList.add(filtroPreProcesamiento);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ManagerException e) {
					e.printStackTrace();
				}
			}
		});
		panelAddPreProcessingFilters.add(btnAddPreProcessingFilter);

		JScrollPane scrollPaneSelectedPreProcessingFilters = new JScrollPane();
		scrollPaneSelectedPreProcessingFilters.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelPreProcessing.add(scrollPaneSelectedPreProcessingFilters, BorderLayout.CENTER);

		gridPreProcessingPanel = new JPanel();
		scrollPaneSelectedPreProcessingFilters.setViewportView(gridPreProcessingPanel);
		FormLayout fl_gridPreProcessingPanel = new FormLayout(new ColumnSpec[] { ColumnSpec.decode("0px:grow"), }, new RowSpec[] { RowSpec.decode("75px"), });
		gridPreProcessingPanel.setLayout(fl_gridPreProcessingPanel);

		JPanel newRowPreProcessing = new JPanel();
		newRowPreProcessing.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gridPreProcessingPanel.add(newRowPreProcessing, "1, 1, fill, fill");
		newRowPreProcessing.setLayout(null);

		JLabel lblPreProcessingFiltername = new JLabel("FilterName");
		lblPreProcessingFiltername.setBounds(20, 27, 255, 14);
		newRowPreProcessing.add(lblPreProcessingFiltername);

		JButton btnPreProcessingDeleteFilter = new JButton("Borrar");
		btnPreProcessingDeleteFilter.setBounds(285, 23, 91, 23);
		newRowPreProcessing.add(btnPreProcessingDeleteFilter);

		JButton btnPreProcessingEditFilter = new JButton("Editar");
		btnPreProcessingEditFilter.setBounds(386, 23, 91, 23);
		newRowPreProcessing.add(btnPreProcessingEditFilter);

		// ----- PIV PROCESAMIENTO PANEL
		JPanel panelPIVProcessing = new JPanel();
		tabbedPaneProcessing.addTab("Procesamiento PIV", null, panelPIVProcessing, null);
		pivProcessingFiltersModel = new DefaultComboBoxModel<ComboItemFilter>();
		panelPIVProcessing.setLayout(new BorderLayout(0, 0));

		JPanel panelAddPIVProcessingFilters = new JPanel();
		FlowLayout fl_panelAddPIVProcessingFilters = (FlowLayout) panelAddPIVProcessingFilters.getLayout();
		fl_panelAddPIVProcessingFilters.setAlignOnBaseline(true);
		fl_panelAddPIVProcessingFilters.setAlignment(FlowLayout.LEFT);
		panelPIVProcessing.add(panelAddPIVProcessingFilters, BorderLayout.NORTH);

		comboBoxPIVProcessing = new JComboBox<ComboItemFilter>();
		panelAddPIVProcessingFilters.add(comboBoxPIVProcessing);
		comboBoxPIVProcessing.setModel(pivProcessingFiltersModel);

		JButton btnAddPIVProcessingFilter = new JButton("Agregar");
		btnAddPIVProcessingFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filterName = ((ComboItemFilter) comboBoxPIVProcessing.getSelectedItem()).getFilterName();
				String filterClass = ((ComboItemFilter) comboBoxPIVProcessing.getSelectedItem()).getFilterClassName();
				FiltroPIV filtroPIVProcesamiento;
				try {
					URLClassLoader filtersClassLoader = FiltersManager.getInstance().getFiltersClassLoader();
					filtroPIVProcesamiento = ((FiltroPIV) Class.forName(filterClass, true, filtersClassLoader).newInstance());
					FilterRowPanel<FiltroPIV> newRowPanel = new FilterRowPanel<FiltroPIV>(filtroPIVProcesamiento, filterName, pivProcessingFilterList);
					newRowPanel.insertRowIn(gridPIVProcessingPanel);
					pivProcessingFilterList.add(filtroPIVProcesamiento);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ManagerException e) {
					e.printStackTrace();
				}
			}
		});
		panelAddPIVProcessingFilters.add(btnAddPIVProcessingFilter);

		JScrollPane scrollPaneSelectedPIVProcessingFilters = new JScrollPane();
		scrollPaneSelectedPIVProcessingFilters.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelPIVProcessing.add(scrollPaneSelectedPIVProcessingFilters, BorderLayout.CENTER);

		gridPIVProcessingPanel = new JPanel();
		scrollPaneSelectedPIVProcessingFilters.setViewportView(gridPIVProcessingPanel);
		FormLayout fl_gridPIVProcessingPanel = new FormLayout(new ColumnSpec[] { ColumnSpec.decode("0px:grow"), }, new RowSpec[] {});
		gridPIVProcessingPanel.setLayout(fl_gridPIVProcessingPanel);

		// ----- POST PROCESAMIENTO PANEL
		JPanel panelPostProcessing = new JPanel();
		tabbedPaneProcessing.addTab("Post-Procesamiento", null, panelPostProcessing, null);
		postProcessingFiltersModel = new DefaultComboBoxModel<ComboItemFilter>();
		panelPostProcessing.setLayout(new BorderLayout(0, 0));

		JPanel panelAddPostProcessingFilters = new JPanel();
		FlowLayout fl_panelAddPostProcessingFilters = (FlowLayout) panelAddPostProcessingFilters.getLayout();
		fl_panelAddPostProcessingFilters.setAlignOnBaseline(true);
		fl_panelAddPostProcessingFilters.setAlignment(FlowLayout.LEFT);
		panelPostProcessing.add(panelAddPostProcessingFilters, BorderLayout.NORTH);

		comboBoxPostProcessing = new JComboBox<ComboItemFilter>();
		panelAddPostProcessingFilters.add(comboBoxPostProcessing);
		comboBoxPostProcessing.setModel(postProcessingFiltersModel);

		JButton btnAddPostProcessingFilter = new JButton("Agregar");
		btnAddPostProcessingFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filterName = ((ComboItemFilter) comboBoxPostProcessing.getSelectedItem()).getFilterName();
				String filterClass = ((ComboItemFilter) comboBoxPostProcessing.getSelectedItem()).getFilterClassName();
				FiltroPostProcesamiento filtroPostProcesamiento;
				try {
					URLClassLoader filtersClassLoader = FiltersManager.getInstance().getFiltersClassLoader();
					filtroPostProcesamiento = ((FiltroPostProcesamiento) Class.forName(filterClass, true, filtersClassLoader).newInstance());
					FilterRowPanel<FiltroPostProcesamiento> newRowPanel = new FilterRowPanel<FiltroPostProcesamiento>(filtroPostProcesamiento, filterName, postProcessingFilterList);
					newRowPanel.insertRowIn(gridPostProcessingPanel);
					postProcessingFilterList.add(filtroPostProcesamiento);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ManagerException e) {
					e.printStackTrace();
				}
			}
		});
		panelAddPostProcessingFilters.add(btnAddPostProcessingFilter);

		JScrollPane scrollPaneSelectedPostProcessingFilters = new JScrollPane();
		scrollPaneSelectedPostProcessingFilters.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelPostProcessing.add(scrollPaneSelectedPostProcessingFilters, BorderLayout.CENTER);

		gridPostProcessingPanel = new JPanel();
		scrollPaneSelectedPostProcessingFilters.setViewportView(gridPostProcessingPanel);
		FormLayout fl_gridPostProcessingPanel = new FormLayout(new ColumnSpec[] { ColumnSpec.decode("0px:grow"), }, new RowSpec[] {});
		gridPostProcessingPanel.setLayout(fl_gridPostProcessingPanel);

		// ----- VISUALIZACION PANEL
		JPanel panelVisualization = new JPanel();
		tabbedPaneProcessing.addTab("Visualizacion", null, panelVisualization, null);
		visualizationFiltersModel = new DefaultComboBoxModel<ComboItemFilter>();
		panelVisualization.setLayout(new BorderLayout(0, 0));

		JPanel panelAddVisualizationFilters = new JPanel();
		FlowLayout fl_panelAddVisualizationFilters = (FlowLayout) panelAddVisualizationFilters.getLayout();
		fl_panelAddVisualizationFilters.setAlignOnBaseline(true);
		fl_panelAddVisualizationFilters.setAlignment(FlowLayout.LEFT);
		panelVisualization.add(panelAddVisualizationFilters, BorderLayout.NORTH);

		comboBoxVisualization = new JComboBox<ComboItemFilter>();
		panelAddVisualizationFilters.add(comboBoxVisualization);
		comboBoxVisualization.setModel(visualizationFiltersModel);

		JButton btnAddVisualizationFilter = new JButton("Agregar");
		btnAddVisualizationFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filterName = ((ComboItemFilter) comboBoxVisualization.getSelectedItem()).getFilterName();
				String filterClass = ((ComboItemFilter) comboBoxVisualization.getSelectedItem()).getFilterClassName();
				FiltroVisualizacion filtroVisualizacion;
				try {
					URLClassLoader filtersClassLoader = FiltersManager.getInstance().getFiltersClassLoader();
					filtroVisualizacion = ((FiltroVisualizacion) Class.forName(filterClass, true, filtersClassLoader).newInstance());
					FilterRowPanel<FiltroVisualizacion> newRowPanel = new FilterRowPanel<FiltroVisualizacion>(filtroVisualizacion, filterName, visualizationFilterList);
					newRowPanel.insertRowIn(gridVisualizationPanel);
					visualizationFilterList.add(filtroVisualizacion);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ManagerException e) {
					e.printStackTrace();
				}
			}
		});
		panelAddVisualizationFilters.add(btnAddVisualizationFilter);

		JScrollPane scrollPaneSelectedVisualizationFilters = new JScrollPane();
		scrollPaneSelectedVisualizationFilters.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelVisualization.add(scrollPaneSelectedVisualizationFilters, BorderLayout.CENTER);

		gridVisualizationPanel = new JPanel();
		scrollPaneSelectedVisualizationFilters.setViewportView(gridVisualizationPanel);
		FormLayout fl_gridVisualizationPanel = new FormLayout(new ColumnSpec[] { ColumnSpec.decode("0px:grow"), }, new RowSpec[] {});
		gridVisualizationPanel.setLayout(fl_gridVisualizationPanel);

		JPanel panelBoludeces = new JPanel();
		panelBoludeces.setToolTipText("Boludeces");
		tabbedPaneProcessing.addTab("Boludeces", null, panelBoludeces, null);

		JButton btnNewButton = new JButton("New button");
		panelBoludeces.add(btnNewButton);

		JButton btnMostrarlistas = new JButton("MostrarListas");
		btnMostrarlistas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Filtro f : preProcessingFilterList) {
					System.out.println(f.getClass().getName());
				}

				for (Filtro f : pivProcessingFilterList) {
					System.out.println(f.getClass().getName());
				}

				for (Filtro f : postProcessingFilterList) {
					System.out.println(f.getClass().getName());
				}
			}
		});
		panelBoludeces.add(btnMostrarlistas);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(imagesList.size());

			}
		});

	}

	public List<FiltroPreProcesamiento> getPreProcessingFilterList() {
		return preProcessingFilterList;
	}

	public List<FiltroPIV> getPivProcessingFilterList() {
		return pivProcessingFilterList;
	}

	public List<FiltroPostProcesamiento> getPostProcessingFilterList() {
		return postProcessingFilterList;
	}

	private void loadFilters() throws ManagerException {
		FiltersManager fm = FiltersManager.getInstance();
		HashMap<String, String> preProcessingFilters = fm.getFiltrosPreProcesamiento();
		HashMap<String, String> pivProcessingFilters = fm.getFiltrosPIVProcesamiento();
		HashMap<String, String> postProcessingFilters = fm.getFiltrosPostProcesamiento();
		HashMap<String, String> visualizationFilters = fm.getFiltrosVisualizacion();

		for (String key : preProcessingFilters.keySet())
			preProcessingFiltersModel.addElement(new ComboItemFilter(key, preProcessingFilters.get(key)));

		for (String key : pivProcessingFilters.keySet())
			pivProcessingFiltersModel.addElement(new ComboItemFilter(key, pivProcessingFilters.get(key)));

		for (String key : postProcessingFilters.keySet())
			postProcessingFiltersModel.addElement(new ComboItemFilter(key, postProcessingFilters.get(key)));

		for (String key : visualizationFilters.keySet())
			visualizationFiltersModel.addElement(new ComboItemFilter(key, visualizationFilters.get(key)));
	}

	public static void restatApplication() {
		try {
			FiltersManager.reloadInstance();
			PIV.dispose();
			new PIVGui();
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
	
	private List<ElementoProcesable> getSelectedImages(){
		List<ElementoProcesable> inputImage = new ArrayList<ElementoProcesable>();

		int[] elem = listImagesFiles.getSelectedIndices();
		for (int i : elem) {
			Imagen im = imagesList.get(i);
			inputImage.add(im);
		}
		
		return inputImage;
	}
	
	private void doPreview(){
		Seleccionador seleccionadorPares = new SeleccionadorPares(Seleccionador.SELECCIONADOR_SIMPLE);
		List<ElementoProcesable> inputImage = getSelectedImages();
		List<ArrayList<ElementoProcesable>> resultList = new ArrayList<ArrayList<ElementoProcesable>>();
		resultList.add((ArrayList<ElementoProcesable>) inputImage);
		for (FiltroPreProcesamiento f : getPreProcessingFilterList()){
			ArrayList<FiltroPreProcesamiento> filterList = new ArrayList<FiltroPreProcesamiento>();
			filterList.add(f);
			Procesador preProcesador = new PreProcesador(filterList, seleccionadorPares);
			try {
				inputImage = preProcesador.procesar(inputImage);
			} catch (WrapperException e) {
				e.printStackTrace();
			}
			resultList.add((ArrayList<ElementoProcesable>) inputImage);
		}
		PreviewFrame previewFrame = new PreviewFrame(resultList);
		previewFrame.setVisible(true);
		previewFrame.pack();
	}

	private List<ElementoProcesable> doPIV() {
		// DEFINIR DESPUES POR CONFIGURACION
		Seleccionador seleccionadorCascada = new SeleccionadorCascada(Seleccionador.SELECCIONADOR_SIMPLE);
		Seleccionador seleccionadorPares = new SeleccionadorPares(Seleccionador.SELECCIONADOR_SIMPLE);
		List<ElementoProcesable> outputPre = null;
		List<ElementoProcesable> outputPIV = null;
		List<ElementoProcesable> outputPost = null;

		try {

			List<ElementoProcesable> inputImage = getSelectedImages();

			Procesador preProcesador = new PreProcesador(getPreProcessingFilterList(), seleccionadorPares);
			Procesador pivProcesador = new ProcesadorPIV(getPivProcessingFilterList().get(0), seleccionadorCascada);
			Procesador postProcesador = new PostProcesador(getPostProcessingFilterList(), seleccionadorPares);

			outputPre = preProcesador.procesar(inputImage);
			outputPIV = pivProcesador.procesar(outputPre);
			outputPost = postProcesador.procesar(outputPIV);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputPost;

	}
	

	private void visualizar(List<ElementoProcesable> visualizationList) {
		for (ElementoProcesable vectorMap : visualizationList)
			for (FiltroVisualizacion filter : visualizationFilterList)
				filter.visualizar(vectorMap);
	}

	private void guardar(List<ElementoProcesable> visualizationList) {
		int dialogResult = JOptionPane.showConfirmDialog(null, "¿Desea guardar los resultados?");
		if (dialogResult == JOptionPane.YES_OPTION) {
			for (ElementoProcesable vectorMap : visualizationList) {
				JFileChooser chooser = new JFileChooser();
				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
					df.applyPattern("+0.0000E00;-0.0000E00");
					try {
						FileHandling.writeArrayToFile(((MapaVectores) vectorMap).getMapaVectores(), chooser.getSelectedFile() + ".jvc", df);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
