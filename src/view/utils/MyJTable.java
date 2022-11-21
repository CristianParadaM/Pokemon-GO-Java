package view.utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import view.JFrame.JFrameMain;

/**
 *  Clase que maneja el objeto MyJTable.java
 *  @author CRISTIAN DAVID PARADA MARTINEZ
 *  @date 2/10/2021
 */
public class MyJTable extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	private JTable jTable;
	private Object[][] info;
	private String[] columnNames;
	private int sizeLetter;
	
	/**
	 * Constructor de la clase MyJTable
	 * @param info informacion de la tabla
	 * @param columnNames cabecera de la tabla
	 * @param sizeLetter tamaño de la letra
	 */
	public MyJTable(Object[][] info, String[] columnNames, int sizeLetter) {
		super();
		this.info = info;
		this.columnNames = columnNames;
		this.sizeLetter = sizeLetter;
		init();
	}
	
	/**
	 * Metodo que crea la tabla, su modelo y su renderizado de celdas
	 */
	private void init() {
		TableModel dataModel = createDataModel();
		initProperties(dataModel);
		applyRender(renderFirstColumn(), renderSecondColumn(), renderThirdColumn());
		this.setViewportView(jTable);
		this.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
	}

	/**
	 * Metodo que inicializa las propiedades de la tabla
	 * @param dataModel
	 */
	private void initProperties(TableModel dataModel) {
		jTable = new JTable(dataModel);
		jTable.setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * sizeLetter )/ 1920)));
		jTable.getTableHeader().setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * sizeLetter )/ 1920)));
		jTable.getTableHeader().setBackground(new Color(255, 90, 90));
		jTable.setRowHeight(100);
	}

	/**
	 * Metodo que aplica el renderizado a las celdas 
	 * @param cellFirstRender render primera columna
	 * @param cellSecondRender render segunda columna
	 * @param cellThirdRender render tercera columna
	 */
	private void applyRender(DefaultTableCellRenderer cellFirstRender, DefaultTableCellRenderer cellSecondRender,
			DefaultTableCellRenderer cellThirdRender) {
		TableColumn fisrstColumn = jTable.getColumn(columnNames[0]);
		TableColumn secondColumn = jTable.getColumn(columnNames[1]);
		TableColumn thirdColumn = jTable.getColumn(columnNames[2]);
		fisrstColumn.setCellRenderer(cellFirstRender);
		secondColumn.setCellRenderer(cellSecondRender);
		thirdColumn.setCellRenderer(cellThirdRender);
	}

	/**
	 * Metodo que crea el modelo de la tabla
	 * @return modelo de tabla
	 */
	private TableModel createDataModel() {
		TableModel dataModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}
			@Override
			public int getRowCount() {
				return info.length;
			}
			@Override
			public Object getValueAt(int row, int col) {
				return info[row][col];
			}
			
			@Override
			public String getColumnName(int column) {
				return columnNames[column];
			}
			@Override
			public Class<? extends Object> getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				info[row][column] = aValue;
			}
		};
		return dataModel;
	}

	/**
	 * Metodo que crea el renderizado de la tercera columna
	 * @return renderizado de celdas
	 */
	private DefaultTableCellRenderer renderThirdColumn() {
		DefaultTableCellRenderer cellRendererPos = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void setValue(Object value) {
				setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * sizeLetter )/ 1920)));
				setForeground(Color.BLACK);
				setBackground(new Color(255, 174, 86));
				super.setValue((int)(((Double)value)+0));
			}
		};
		cellRendererPos.setHorizontalAlignment(JLabel.CENTER);
		return cellRendererPos;
	}

	/**
	 * Metodo que crea el renderizado de la segunda columna
	 * @return renderizado de celdas
	 */
	private DefaultTableCellRenderer renderSecondColumn() {
		DefaultTableCellRenderer cellRendererName = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void setValue(Object value) {
				setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * sizeLetter )/ 1920)));
				setForeground(Color.BLACK);
				setBackground(new Color(71, 199, 255));
				super.setValue(value);
			}
		};
		cellRendererName.setHorizontalAlignment(JLabel.CENTER);
		return cellRendererName;
	}

	/**
	 * Metodo que crea el renderizado de la primera columna
	 * @return renderizado de celdas
	 */
	private DefaultTableCellRenderer renderFirstColumn() {
		DefaultTableCellRenderer cellRenderAmount = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void setValue(Object value) {
				setFont(new Font("Ravie", Font.BOLD, (int)((JFrameMain.WIDTH_SCREEN * sizeLetter )/ 1920)));
				setForeground(Color.BLACK);
				setBackground(new Color(255, 243, 86));
				if (value instanceof String) {
					super.setValue(value);
				}else {
					super.setValue((int)(((Double)value)+0));
				}
			}
		};
		cellRenderAmount.setHorizontalAlignment(JLabel.CENTER);
		return cellRenderAmount;
	}
	
}
