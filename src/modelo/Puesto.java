
package modelo;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;


public class Puesto{
    private int id_po;
    private String puestos;
    Conexion cn;
    
    public Puesto(){}

    public Puesto(int id_po, String puesto) {
       this.id_po = id_po;
       this.puestos = puesto;
    }

    public int getId_po() {
        return id_po;
    }

    public void setId_po(int id_po) {
        this.id_po = id_po;
    }

    public String getPuesto() {
        return puestos;
    }

    public void setPuesto(String puesto) {
        this.puestos = puesto;
    }

public HashMap<String, Integer> map = new HashMap<>();

public DefaultComboBoxModel<String> leer() {
    DefaultComboBoxModel<String> p = new DefaultComboBoxModel<>();
    try {
        cn = new Conexion();
        cn.abrir_conexion();
        String query = "select * from puestos;";
      ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        while (consulta.next()) {
            int id = consulta.getInt("id_puesto");
            String puesto = consulta.getString("puesto");
           p.addElement(puesto);
            map.put(puesto,id);
        }
        cn.cerrar_conexion();
    } catch (SQLException ex) {
        cn.cerrar_conexion();
        JOptionPane.showMessageDialog(null, "Error al leer los datos: " + ex.getMessage());
    }
    return p;
}

}
