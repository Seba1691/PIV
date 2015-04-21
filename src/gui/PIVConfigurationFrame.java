package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pivLayer.Filtro;

public class PIVConfigurationFrame extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	//private Filtro filtro;

	/**
	 * Create the frame.
	 */
	public PIVConfigurationFrame(Filtro filtro) {
		//this.filtro = filtro;		
		initComponent();
		InitFrame();	

		pack();
	}
	
	private void initComponent(){
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(120, 100, 225, 259);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBorder(new EmptyBorder(20, 25, 25, 25));
		setContentPane(contentPane);
		
		jScrollPaneRight = new javax.swing.JScrollPane();		
		jPanelPivWindow = new javax.swing.JPanel();
		jLabelPivMultiPass = new javax.swing.JLabel();
		jSpinnerPivMultiPass = new javax.swing.JSpinner(
				new javax.swing.SpinnerNumberModel(1, 1, 9, 1));
		jLabelPivWndWidth = new javax.swing.JLabel();
		jLabelPivWndHeight = new javax.swing.JLabel();
		jLabelPivHorVecSpacing = new javax.swing.JLabel();
		jLabelPivVerVecSpacing = new javax.swing.JLabel();
		jLabelPivDomainWidth = new javax.swing.JLabel();
		jLabelPivDomainHeight = new javax.swing.JLabel();
		jLabelPivHorPreShift = new javax.swing.JLabel();
		jTextFieldPivHorPreShift = new javax.swing.JTextField();
		jLabelPivVerPreShift = new javax.swing.JLabel();
		jTextFieldPivVerPreShift = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTablePivWindow = new javax.swing.JTable();
		jTablePivWindow.putClientProperty("terminateEditOnFocusLost",
				Boolean.TRUE);
		jLabelPivBetweenPasses = new javax.swing.JLabel();
		jCheckBoxPivReplace = new javax.swing.JCheckBox();
		jCheckBoxPivMedian = new javax.swing.JCheckBox();
		jTextFieldPivROIP2y = new javax.swing.JTextField();
		jTextFieldPivROIP1y = new javax.swing.JTextField();
		jLabelPivROIY1 = new javax.swing.JLabel();
		jLabelPivROIX = new javax.swing.JLabel();
		jTextFieldPivROIP1x = new javax.swing.JTextField();
		jTextFieldPivROIP2x = new javax.swing.JTextField();
		jLabelPivROIRight = new javax.swing.JLabel();
		jLabelPivROILeft = new javax.swing.JLabel();
		jCheckBoxPivROI = new javax.swing.JCheckBox();
		jSeparator7 = new javax.swing.JSeparator();
		jSeparator8 = new javax.swing.JSeparator();
		jSeparator9 = new javax.swing.JSeparator();
		jCheckBoxPivNormMedianTest = new javax.swing.JCheckBox();
		jCheckBoxPivSmoothing = new javax.swing.JCheckBox();
		jSeparator14 = new javax.swing.JSeparator();
		jCheckBoxPivShearIntWindows = new javax.swing.JCheckBox();
		jSeparator15 = new javax.swing.JSeparator();
		jCheckBoxExpCorrFunc = new javax.swing.JCheckBox();
		jLabelExpCorrFuncVec = new javax.swing.JLabel();
		jLabelExpCorrFuncPass = new javax.swing.JLabel();
		jTextFieldExpCorrFuncVec = new javax.swing.JTextField();
		jTextFieldExpCorrFuncPass = new javax.swing.JTextField();
		jCheckBoxExpCorrFuncSum = new javax.swing.JCheckBox();
		

		contentPane.add(jScrollPaneRight);
		
	}

	private void InitFrame() {
		jPanelPivWindow.setPreferredSize(new java.awt.Dimension(500, 700));
		jPanelPivWindow.setLayout(null);

		jLabelPivMultiPass.setText("Multi pass");
		jPanelPivWindow.add(jLabelPivMultiPass);
		jLabelPivMultiPass.setBounds(10, 10, 220, 19);

		jSpinnerPivMultiPass
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						jSpinnerPivMultiPassStateChanged(evt);
					}
				});
		jPanelPivWindow.add(jSpinnerPivMultiPass);
		jSpinnerPivMultiPass.setBounds(240, 10, 40, 24);

		jLabelPivWndWidth.setText("Interrogation window width");
		jPanelPivWindow.add(jLabelPivWndWidth);
		jLabelPivWndWidth.setBounds(10, 55, 220, 19);

		jLabelPivWndHeight.setText("Interrogation window height");
		jPanelPivWindow.add(jLabelPivWndHeight);
		jLabelPivWndHeight.setBounds(10, 71, 220, 19);

		jLabelPivHorVecSpacing.setText("Horizontal vector spacing");
		jPanelPivWindow.add(jLabelPivHorVecSpacing);
		jLabelPivHorVecSpacing.setBounds(10, 119, 220, 19);

		jLabelPivVerVecSpacing.setText("Vertical vector spacing");
		jPanelPivWindow.add(jLabelPivVerVecSpacing);
		jLabelPivVerVecSpacing.setBounds(10, 135, 220, 19);

		jLabelPivDomainWidth.setText("Search domain width");
		jPanelPivWindow.add(jLabelPivDomainWidth);
		jLabelPivDomainWidth.setBounds(10, 87, 220, 19);

		jLabelPivDomainHeight.setText("Search domain height");
		jPanelPivWindow.add(jLabelPivDomainHeight);
		jLabelPivDomainHeight.setBounds(10, 103, 220, 19);

		jLabelPivHorPreShift.setText("Horizontal pre-shift (fist pass)");
		jPanelPivWindow.add(jLabelPivHorPreShift);
		jLabelPivHorPreShift.setBounds(10, 280, 220, 19);

		jTextFieldPivHorPreShift.setText("jTextField1");
		jTextFieldPivHorPreShift.setInputVerifier(new jpiv2.VerifierInt());
		jTextFieldPivHorPreShift
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jTextFieldPivHorPreShift);
		jTextFieldPivHorPreShift.setBounds(240, 280, 80, 23);

		jLabelPivVerPreShift.setText("Vertical pre-shift (first pass)");
		jPanelPivWindow.add(jLabelPivVerPreShift);
		jLabelPivVerPreShift.setBounds(10, 300, 220, 19);

		jTextFieldPivVerPreShift.setText("jTextField2");
		jTextFieldPivVerPreShift.setInputVerifier(new jpiv2.VerifierInt());
		jTextFieldPivVerPreShift
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jTextFieldPivVerPreShift);
		jTextFieldPivVerPreShift.setBounds(240, 300, 80, 23);

		jTablePivWindow.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		jTablePivWindow.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jScrollPane1.setViewportView(jTablePivWindow);

		jPanelPivWindow.add(jScrollPane1);
		jScrollPane1.setBounds(240, 40, 240, 114);

		jLabelPivBetweenPasses.setText("Between passes:");
		jPanelPivWindow.add(jLabelPivBetweenPasses);
		jLabelPivBetweenPasses.setBounds(10, 340, 220, 19);

		jCheckBoxPivReplace.setText("Replace invalid vecors by median");
		jCheckBoxPivReplace.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jCheckBoxPivReplace);
		jCheckBoxPivReplace.setBounds(240, 360, 240, 27);

		jCheckBoxPivMedian.setText("3x3 median filter");
		jCheckBoxPivMedian.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jCheckBoxPivMedian);
		jCheckBoxPivMedian.setBounds(240, 380, 240, 27);

		jTextFieldPivROIP2y.setText("jTextField4");
		jTextFieldPivROIP2y.setInputVerifier(new jpiv2.VerifierUInt());
		jTextFieldPivROIP2y.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jTextFieldPivROIP2y);
		jTextFieldPivROIP2y.setBounds(190, 240, 62, 23);

		jTextFieldPivROIP1y.setText("jTextField3");
		jTextFieldPivROIP1y.setInputVerifier(new jpiv2.VerifierUInt());
		jTextFieldPivROIP1y.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jTextFieldPivROIP1y);
		jTextFieldPivROIP1y.setBounds(110, 240, 62, 23);

		jLabelPivROIY1.setText("y [pixel]");
		jPanelPivWindow.add(jLabelPivROIY1);
		jLabelPivROIY1.setBounds(30, 240, 70, 19);

		jLabelPivROIX.setText("x [pixel]");
		jPanelPivWindow.add(jLabelPivROIX);
		jLabelPivROIX.setBounds(30, 220, 70, 19);

		jTextFieldPivROIP1x.setText("jTextField1");
		jTextFieldPivROIP1x.setInputVerifier(new jpiv2.VerifierUInt());
		jTextFieldPivROIP1x.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jTextFieldPivROIP1x);
		jTextFieldPivROIP1x.setBounds(110, 220, 62, 23);

		jTextFieldPivROIP2x.setText("jTextField2");
		jTextFieldPivROIP2x.setInputVerifier(new jpiv2.VerifierUInt());
		jTextFieldPivROIP2x.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jTextFieldPivROIP2x);
		jTextFieldPivROIP2x.setBounds(190, 220, 62, 23);

		jLabelPivROIRight.setText("bottom right");
		jPanelPivWindow.add(jLabelPivROIRight);
		jLabelPivROIRight.setBounds(190, 200, 90, 19);

		jLabelPivROILeft.setText("top left");
		jPanelPivWindow.add(jLabelPivROILeft);
		jLabelPivROILeft.setBounds(110, 200, 70, 19);

		jCheckBoxPivROI.setText("Region of interest (ROI)");
		jCheckBoxPivROI.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				jPanelPivWindowInputComponentFocusLost(evt);
			}
		});
		jPanelPivWindow.add(jCheckBoxPivROI);
		jCheckBoxPivROI.setBounds(10, 170, 250, 27);
		jPanelPivWindow.add(jSeparator7);
		jSeparator7.setBounds(10, 160, 500, 10);
		jPanelPivWindow.add(jSeparator8);
		jSeparator8.setBounds(10, 330, 500, 10);
		jPanelPivWindow.add(jSeparator9);
		jSeparator9.setBounds(10, 270, 500, 10);

		jCheckBoxPivNormMedianTest.setText("Normalized median test");
		jCheckBoxPivNormMedianTest
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jCheckBoxPivNormMedianTest);
		jCheckBoxPivNormMedianTest.setBounds(240, 340, 240, 27);

		jCheckBoxPivSmoothing.setText("3x3 smoothing");
		jCheckBoxPivSmoothing
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jCheckBoxPivSmoothing);
		jCheckBoxPivSmoothing.setBounds(240, 400, 240, 27);
		jPanelPivWindow.add(jSeparator14);
		jSeparator14.setBounds(10, 430, 500, 10);

		jCheckBoxPivShearIntWindows
				.setText("Deform (shear) interrogation windows");
		jCheckBoxPivShearIntWindows
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jCheckBoxPivShearIntWindows);
		jCheckBoxPivShearIntWindows.setBounds(10, 440, 500, 27);
		jPanelPivWindow.add(jSeparator15);
		jSeparator15.setBounds(10, 470, 500, 10);

		jCheckBoxExpCorrFunc.setText("Export correlation functions");
		jCheckBoxExpCorrFunc
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jCheckBoxExpCorrFunc);
		jCheckBoxExpCorrFunc.setBounds(10, 480, 230, 27);

		jLabelExpCorrFuncVec
				.setText("Vector (zero from upper left corner, -1 for all)");
		jPanelPivWindow.add(jLabelExpCorrFuncVec);
		jLabelExpCorrFuncVec.setBounds(10, 510, 310, 19);

		jLabelExpCorrFuncPass.setText("Pass (starting with zero, -1 for all)");
		jPanelPivWindow.add(jLabelExpCorrFuncPass);
		jLabelExpCorrFuncPass.setBounds(10, 530, 310, 19);

		jTextFieldExpCorrFuncVec.setText("0");
		jTextFieldExpCorrFuncVec.setInputVerifier(new jpiv2.VerifierInt());
		jTextFieldExpCorrFuncVec
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jTextFieldExpCorrFuncVec);
		jTextFieldExpCorrFuncVec.setBounds(320, 500, 80, 23);

		jTextFieldExpCorrFuncPass.setText("0");
		jTextFieldExpCorrFuncPass.setInputVerifier(new jpiv2.VerifierInt());
		jTextFieldExpCorrFuncPass
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jTextFieldExpCorrFuncPass);
		jTextFieldExpCorrFuncPass.setBounds(320, 520, 80, 23);

		jCheckBoxExpCorrFuncSum.setText("Only Sum Of Correlation");
		jCheckBoxExpCorrFuncSum
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						jPanelPivWindowInputComponentFocusLost(evt);
					}
				});
		jPanelPivWindow.add(jCheckBoxExpCorrFuncSum);
		jCheckBoxExpCorrFuncSum.setBounds(10, 550, 230, 27);

		jScrollPaneRight.setViewportView(jPanelPivWindow);

	}
	private void jPanelPivWindowInputComponentFocusLost(
			java.awt.event.FocusEvent evt) {// GEN-FIRST:event_jPanelPivWindowInputComponentFocusLost
		if (jTablePivWindow.isEditing()) {
			jTablePivWindow.getCellEditor().stopCellEditing();
		}
		//getPivWindowSettings(jpiv.getSettings());
	}// GEN-LAST:event_jPanelPivWindowInputComponentFocusLost
	
	private void jSpinnerPivMultiPassStateChanged(
			javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSpinnerPivMultiPassStateChanged
		//updateJTablePivWindow(jpiv.getSettings());
		//getPivWindowSettings(jpiv.getSettings());
	}// GEN-LAST:event_jSpinnerPivMultiPassStateChanged

		private javax.swing.JCheckBox jCheckBoxExpCorrFunc;
		private javax.swing.JCheckBox jCheckBoxExpCorrFuncSum;

		private javax.swing.JCheckBox jCheckBoxPivMedian;
		private javax.swing.JCheckBox jCheckBoxPivNormMedianTest;
		private javax.swing.JCheckBox jCheckBoxPivROI;
		private javax.swing.JCheckBox jCheckBoxPivReplace;
		private javax.swing.JCheckBox jCheckBoxPivShearIntWindows;
		private javax.swing.JCheckBox jCheckBoxPivSmoothing;

		private javax.swing.JLabel jLabelExpCorrFuncPass;
		private javax.swing.JLabel jLabelExpCorrFuncVec;
		private javax.swing.JLabel jLabelPivBetweenPasses;
		private javax.swing.JLabel jLabelPivDomainHeight;
		private javax.swing.JLabel jLabelPivDomainWidth;
		private javax.swing.JLabel jLabelPivHorPreShift;
		private javax.swing.JLabel jLabelPivHorVecSpacing;
		private javax.swing.JLabel jLabelPivMultiPass;
		private javax.swing.JLabel jLabelPivROILeft;
		private javax.swing.JLabel jLabelPivROIRight;
		private javax.swing.JLabel jLabelPivROIX;
		private javax.swing.JLabel jLabelPivROIY1;
		private javax.swing.JLabel jLabelPivVerPreShift;
		private javax.swing.JLabel jLabelPivVerVecSpacing;
		private javax.swing.JLabel jLabelPivWndHeight;
		private javax.swing.JLabel jLabelPivWndWidth;

		private javax.swing.JPanel jPanelPivWindow;
		private javax.swing.JScrollPane jScrollPane1;
		private javax.swing.JScrollPane jScrollPaneRight;
		private javax.swing.JSeparator jSeparator14;
		private javax.swing.JSeparator jSeparator15;
		private javax.swing.JSeparator jSeparator7;
		private javax.swing.JSeparator jSeparator8;
		private javax.swing.JSeparator jSeparator9;
		private javax.swing.JSpinner jSpinnerPivMultiPass;
		private javax.swing.JTable jTablePivWindow;
		private javax.swing.JTextField jTextFieldExpCorrFuncPass;
		private javax.swing.JTextField jTextFieldExpCorrFuncVec;
		private javax.swing.JTextField jTextFieldPivHorPreShift;
		private javax.swing.JTextField jTextFieldPivROIP1x;
		private javax.swing.JTextField jTextFieldPivROIP1y;
		private javax.swing.JTextField jTextFieldPivROIP2x;
		private javax.swing.JTextField jTextFieldPivROIP2y;
		private javax.swing.JTextField jTextFieldPivVerPreShift;

}
