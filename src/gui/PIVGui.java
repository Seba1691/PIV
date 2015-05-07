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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
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
import pivLayer.Imagen;
import pivLayer.MapaVectores;
import pivLayer.PostProcesador;
import pivLayer.PreProcesador;
import pivLayer.Procesador;
import pivLayer.ProcesadorPIV;
import pivLayer.Seleccionador;
import pivLayer.SeleccionadorCascada;
import pivLayer.SeleccionadorPares;
import wapper.JPIVWrapper;
import cache.CacheManager;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import filters.FiltroAutoLocalTreshold;

public class PIVGui {

	private static JFrame PIV;

	private DefaultListModel<File> selectedFilesModel;
	private JList<File> listFiles;

	private DefaultComboBoxModel<ComboItemFilter> preProcessingFiltersModel;
	private DefaultComboBoxModel<ComboItemFilter> postProcessingFiltersModel;
	private DefaultComboBoxModel<ComboItemFilter> pivProcessingFiltersModel;

	private JPanel gridPreProcessingPanel;
	private JComboBox<ComboItemFilter> comboBoxPreProcessing;
	private JPanel gridPIVProcessingPanel;
	private JComboBox<ComboItemFilter> comboBoxPIVProcessing;
	private JPanel gridPostProcessingPanel;
	private JComboBox<ComboItemFilter> comboBoxPostProcessing;

	private List<FiltroPreProcesamiento> preProcessingFilterList;
	private List<FiltroPIV> pivProcessingFilterList;
	private List<FiltroPostProcesamiento> postProcessingFilterList;
	private List<Imagen> imagesList;

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

		new PostProcesador((ArrayList<FiltroPostProcesamiento>) postProcessingFilterList, new SeleccionadorCascada(Seleccionador.SELECCIONADOR_DOBLE));

	}

	public void addSelectedFile(File f) {
		this.selectedFilesModel.addElement(f);
		try {
			imagesList.add(new Imagen(ImageIO.read(f)));
		} catch (IOException e) {
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
		PIV.setBounds(100, 100, 809, 495);
		PIV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PIV.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		PIV.getContentPane().add(splitPane, BorderLayout.CENTER);

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
						System.out.println("Opening: " + f.getName() + ".");
						addSelectedFile(f);
					}
				} else {
					System.out.println("Open command cancelled by user.");
				}

			}
		});
		menuFile.add(itemChooseFile);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		menuFile.add(mntmNewMenuItem_1);

		JMenu menuPIV = new JMenu("PIV");
		menuBar.add(menuPIV);

		JMenuItem itemDoPIV = new JMenuItem("Ejecutar PIV");
		itemDoPIV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doPIV();
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
		selectedFilesModel = new DefaultListModel<File>();
		imagesList = new ArrayList<Imagen>();
		JTabbedPane tabbedPaneFiles = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneFiles.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		splitPane.setLeftComponent(tabbedPaneFiles);

		JScrollPane scrollPaneFiles = new JScrollPane();
		tabbedPaneFiles.addTab("Archivos", null, scrollPaneFiles, null);

		listFiles = new JList<File>();
		listFiles.setModel(selectedFilesModel);
		scrollPaneFiles.setViewportView(listFiles);

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

		for (String key : preProcessingFilters.keySet())
			preProcessingFiltersModel.addElement(new ComboItemFilter(key, preProcessingFilters.get(key)));

		for (String key : pivProcessingFilters.keySet())
			pivProcessingFiltersModel.addElement(new ComboItemFilter(key, pivProcessingFilters.get(key)));

		for (String key : postProcessingFilters.keySet())
			postProcessingFiltersModel.addElement(new ComboItemFilter(key, postProcessingFilters.get(key)));
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

	private void doPIV() {
		// DEFINIR DESPUES POR CONFIGURACION
		Seleccionador seleccionadorCascada = new SeleccionadorCascada(Seleccionador.SELECCIONADOR_SIMPLE);
		Seleccionador seleccionadorPares = new SeleccionadorPares(Seleccionador.SELECCIONADOR_SIMPLE);

		try {

			List<ElementoProcesable> inputImage = new ArrayList<ElementoProcesable>();

			int[] elem = listFiles.getSelectedIndices();
			for (int i : elem) {
				Imagen im = imagesList.get(i);
				inputImage.add(im);
			}

			Procesador preProcesador = new PreProcesador(getPreProcessingFilterList(), seleccionadorPares);

			Procesador pivProcesador = new ProcesadorPIV(getPivProcessingFilterList().get(0), seleccionadorCascada);

			Procesador postProcesador = new PostProcesador(getPostProcessingFilterList(), seleccionadorPares);

			List<ElementoProcesable> outputPre = preProcesador.procesar(inputImage);

			List<ElementoProcesable> outputPIV = pivProcesador.procesar(outputPre);

			List<ElementoProcesable> outputPost = postProcesador.procesar(outputPIV);

			// DecimalFormat df = (DecimalFormat)
			// DecimalFormat.getInstance(Locale.US);
			// df.applyPattern("+0.0000E00;-0.0000E00");
			// FileHandling.writeArrayToFile(((MapaVectores)
			// outputPost.get(0)).getMapaVectores(),
			// "C:/Users/Seba/Desktop/PIV/Salidas/pruebita.jvc", df);
			// JPIVWrapper.visualizar(new MapaVectores(FileHandling.readArrayFromFile("C:/Users/Seba/Desktop/PIV/Salidas/pruebita.jvc")));

			JPIVWrapper.visualizar((MapaVectores) outputPost.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
