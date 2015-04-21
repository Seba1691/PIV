package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pivLayer.Filtro;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class FilterRowPanel<T extends Filtro> extends JPanel {

	private static final long serialVersionUID = 1L;

	private Filtro filtro;
	private JPanel filtersPanel;
	private List<T> filterList;

	public FilterRowPanel(Filtro filtro, String filterName, List<T> filterList) {
		this.filtro = filtro;
		this.filterList = filterList;
		createFilterRowPanel(filterName);
	}

	public void insertRowIn(JPanel filtersPanel) {
		this.filtersPanel = filtersPanel;
		FormLayout layoutRowFilters = (FormLayout) filtersPanel.getLayout();
		layoutRowFilters.appendRow(RowSpec.decode("75px"));
		int n = layoutRowFilters.getRowCount();
		filtersPanel.add(this, "1, " + n + ", fill, fill");

	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	private void createFilterRowPanel(String filterName) {

		setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JLabel lblFiltername = new JLabel(filterName);
		lblFiltername.setBounds(20, 27, 255, 14);
		add(lblFiltername);

		JButton btnDeleteFilter = new JButton("Borrar");
		btnDeleteFilter.setBounds(285, 23, 91, 23);
		btnDeleteFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel removeRowPanel = (JPanel) ((JButton) e.getSource()).getParent();
				FormLayout layoutRowFilters = (FormLayout) filtersPanel.getLayout();
				int n = layoutRowFilters.getConstraints(removeRowPanel).gridY;
				removeRowPanel.removeAll();
				JPanel parentPanel = (JPanel) removeRowPanel.getParent();
				parentPanel.remove(removeRowPanel);
				layoutRowFilters.removeLayoutComponent(removeRowPanel);
				layoutRowFilters.removeRow(n);
				parentPanel.repaint();
				filterList.remove(filtro);

			}
		});
		add(btnDeleteFilter);

		JButton btnEditFilter = new JButton("Editar");
		btnEditFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilterConfigurationFrame frame = new FilterConfigurationFrame(filtro);
				frame.setVisible(true);
				frame.setLocationRelativeTo(getParent());
			}
		});

		btnEditFilter.setBounds(386, 23, 91, 23);
		if (filtro.getParametros() == null)
			btnEditFilter.setEnabled(false);
		add(btnEditFilter);
	}

}
