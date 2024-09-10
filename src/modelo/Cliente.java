/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Tejada
 */
public class Cliente extends Persona {
    private String nit;
    private String id;
    Conexion cn;

    public Cliente() {}

    public Cliente(String id, String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.nit = nit;
        this.id = id;
    }

    public Conexion getCn() {
        return cn;
    }

    public void setCn(Conexion cn) {
        this.cn = cn;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public DefaultTableModel leer(){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "SELECT id_empleado, nombres, apellidos, direccion, telefono, fecha_nacimiento FROM db_empleadoss.empleados";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
            
            String encabezado[] = {"id", "Nombres", "Apellidos", "Direccion", "Telefono", "Nacimiento"};
            tabla.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[6];  // Cambiado a 6 ya que no hay 'nit'
            while(consulta.next()){
                datos[0] = consulta.getString("id_empleado");
                datos[1] = consulta.getString("nombres");
                datos[2] = consulta.getString("apellidos");
                datos[3] = consulta.getString("direccion");
                datos[4] = consulta.getString("telefono");
                datos[5] = consulta.getString("fecha_nacimiento");
                tabla.addRow(datos);
            }
            
            cn.cerrar_conexion();
            
        } catch(SQLException ex) {
            cn.cerrar_conexion();
            System.out.println("Error: " + ex.getMessage());
        }
        return tabla;
    }

    // CRUD
    @Override
    public String[] crear(){
        try{
            PreparedStatement parametro;
            String query = "INSERT INTO empleados(codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento) VALUES(?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro ingresado", "Agregar", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex){
            System.out.println("Algo salió mal: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void actualizar(){
        try{
            PreparedStatement parametro;
            String query = "UPDATE empleados SET codigo = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ? WHERE id_empleado = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getId());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro actualizado", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex){
            System.out.println("Algo salió mal: " + ex.getMessage());
        }
    }

    public void eliminar(){
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM empleados WHERE id_empleado = ?";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getId());
            
            int executar = parametro.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro Eliminado " + Integer.toString(executar));
            cn.cerrar_conexion();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
        }
    }
}
