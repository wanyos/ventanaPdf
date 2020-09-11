
package com.wanyos.init;


import com.wanyos.manager.*;
import com.wanyos.vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * 
 * @author wanyos
 */
public class Init {

    private FrameInit fi;
    private JButton btn_ejecutar;
    private AbstractPanel pn_abs;
    
    
    public Init(){
        fi = new FrameInit();
        this.btn_ejecutar = fi.getBtnEjecutar();
        setListenerBtn();
    }
    
    
    /**
     * Inicia el proceso de conversion 
     */
    private void setListenerBtn(){
        this.btn_ejecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               pn_abs = fi.getPnAbs();
               ejecutarConversion(pn_abs);
            }
        });
    }
    
    
    private void ejecutarConversion(AbstractPanel pn_abstract){
        String name_pn = pn_abstract.getName();
        String msg = "";
        
        switch(name_pn){
            
            case "cuadros":
                if (pn_abstract.getBaseDatos()) {                       //si correcto vamos a base de datos cuadro
                    comprobarCuadroBD(pn_abstract);
                    msg = prepareCuadroBD(pn_abstract);
                    fi.setTxtMensaje(msg);
                } else if (comprobarCuadroArchivo(pn_abstract)) {       //si se cumple vamos a archivo cuadro
                    msg = prepareCuadroArchivo(pn_abstract);
                    fi.setTxtMensaje(msg);
                }
                break;

            case "generados":

                break;

            case "minutos":

                break;

            default:
                fi.setTxtMensaje("Nombre opción no existe...");
                break;
        }
    }
    
    
    private boolean comprobarCuadroBD(AbstractPanel pn_abstract){
        if(!existeRutaBD(pn_abstract)){
               JOptionPane.showMessageDialog(pn_abs, "Error en la ruta de pdf o archivo pdf... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
               return false;
           } else {
               //comprobar si bd nueva
               if(pn_abstract.getArchivoNuevo() && !existeNombreBD(pn_abstract)){
                 JOptionPane.showMessageDialog(pn_abs, "Error nombre base de datos nueva... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
                 return false;
               } 
           }
        return true;
    }
    
    
    private boolean comprobarCuadroArchivo(AbstractPanel pn_abs) {
        if (pn_abs.isTodosArchivos()) {
            if (!existeRutaPdfArchivo(pn_abs)) {
                JOptionPane.showMessageDialog(pn_abs, "Error en la ruta de pdf o ruta destino... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (!existeRutaPdfArchivo(pn_abs) || !pn_abs.getRutaArchivoPdf().exists()) {
            JOptionPane.showMessageDialog(pn_abs, "Error en la ruta de pdf, archivo pdf o ruta destino... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    
    
    private boolean existeRutaBD(AbstractPanel pn_abs){
        return pn_abs.getRutaPdf().exists() && pn_abs.getRutaArchivoPdf().exists();
    }
    
    private boolean existeNombreBD(AbstractPanel pn_abs){
        return pn_abs.getNombreBD() == null || pn_abs.getNombreBD().length() <= 0;
    }
    
    
    private boolean existeRutaPdfArchivo(AbstractPanel pn_abs){
        return pn_abs.getRutaPdf().exists() && pn_abs.getRutaDestino().exists();
    }
    
    
    private String prepareCuadroBD(AbstractPanel pn_abs){
        String msg = "";
        //InitCuadros inc = new InitCuadros();
        
        
        return msg;
    }
    
    
    private String prepareCuadroArchivo(AbstractPanel pn_abs) {
        InitCuadros inc;
        String nombre_archivo_pdf, nombre_destino;
        String mensaje;

        if (pn_abs.isTodosArchivos()) {
            inc = new InitCuadros(pn_abs);
            mensaje = inc.setArchivosCuadros();
        } else {
            nombre_archivo_pdf = pn_abs.getRutaArchivoPdf().getName();
            nombre_destino = this.comprobarNombreDestino(pn_abs.getNombreDestino(), nombre_archivo_pdf);
            inc = new InitCuadros(pn_abs, nombre_archivo_pdf, nombre_destino);
            mensaje = inc.setArchivoCuadro();
        }
        return mensaje;
    }
    
    
    /**
     * Si el nombre destino no es valido se crea uno basado en el nombre del pdf leido
     * @param nombre_destino
     * @param nombre_pdf
     * @return 
     */
    private String comprobarNombreDestino(String nombre_destino, String nombre_pdf){
        String nombre = "";
        if(nombre_destino == null || nombre_destino.trim().length() == 0 || !nombre_destino.contains(".txt")){
            String sub = nombre_pdf.substring(0, nombre_pdf.length()-4);
            nombre = nombre.concat("nuevo_").concat(sub).concat(".txt");
            return nombre;
        }
        return nombre_destino;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Si el archivo existe devuelve su nombre
     * @param archivo_pdf
     * @return 
     */
//    private String getNombrePdf(File archivo_pdf){
//        String nombre = null;
//        if(archivo_pdf != null && archivo_pdf.exists()){
//            nombre = archivo_pdf.getName();
//        }
//        return nombre;
//    }
//    
    
    
    
//    private void ejecutarConversion(AbstractPanel pn_abs) {
//        String name = pn_abs.getName();
//        boolean bd = pn_abs.getBaseDatos();
//        //se gestionan archivos o base de datos
//        if (name.equalsIgnoreCase("cuadros")) {
//            if (bd) {
//                //base datos
//              setCuadrosBD(pn_abs.getNombreBD());
//            } else {
//                setCuadros(pn_abs);
//            }
//
//        } else if (name.equalsIgnoreCase("generados")) {
//            if (bd) {
//                //base datos 
//                
//            } else {
//                setGenerados(pn_abs);
//            }
//
//        } else if (name.equalsIgnoreCase("minutos")) {
//            if (bd) {
//                //base datos 
//                
//            } else {
//                setMinutos(pn_abs);
//            }
//        }
//    }
    
    
    
    /**
     * Siempre se necesita la ruta del pdf a leer y la ruta del archivo de destino pra archivos
     * Para base de datos ruta pdf y nombre pdf 
     * @param pn_abs
     * @return 
     */
//    private boolean comprobarRutas(AbstractPanel pn_abs) {
//        try {
//            if (pn_abs.getBaseDatos() && pn_abs.getRutaPdf().exists() && pn_abs.getRutaArchivoPdf().exists()) {
//                if (pn_abs.getArchivoNuevo() && (pn_abs.getNombreBD() == null || pn_abs.getNombreBD().length() <= 0)) {
//                    JOptionPane.showMessageDialog(pn_abs, "Error nombre base datos incorrecto... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    return true;
//                }
//            } else if (!pn_abs.getRutaPdf().exists()) {
//                JOptionPane.showMessageDialog(pn_abs, "Error en la ruta de pdf... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
//            } else if (!pn_abs.getRutaDestino().exists()) {
//                JOptionPane.showMessageDialog(pn_abs, "Error en la ruta destino... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
//            } else {
//                return true;
//            }
//        } catch (NullPointerException e) {
//            JOptionPane.showMessageDialog(pn_abs, "Error en la ruta de pdf o ruta destino... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        return false;
//    }
    
    
    
    
    
    
    
    
    
    
//    private void setCuadrosBD(String nombre_bd){
//        //si nombre_bd == null hay que actualizar sino una nueva
//        if(nombre_bd != null){
//            System.out.println();
//        }
//    }

    
    /**
     * Escribe el archivo o archivos con los cuadros
     * @param pn_abstract 
     */
//    private void setCuadros(AbstractPanel pn_abstract) {
//        ManagerCuadros mc = new ManagerCuadros();
//        boolean sin_cabecera;
//        String ruta_pdf = pn_abstract.getRutaPdf().getAbsolutePath();
//        String ruta_destino = pn_abstract.getRutaDestino().getAbsolutePath();
//        String nombre_archivo_pdf = "";
//        String nombre_destino;
//        String total_datos;
//        boolean escrito;
//
//        if (pn_abstract.isTodosArchivos()) {
//            //escribir todos los archivos del directorio
//            sin_cabecera = pn_abstract.getSinCabecera();
//            mc.writeArchivosCuadros(ruta_pdf, ruta_destino, sin_cabecera);
//        } else {
//            //escribir solo un archivo con el pdf
//            //comprueba que existe y devuelve su nombre
//            nombre_archivo_pdf = this.getNombrePdf(pn_abstract.getRutaArchivoPdf());
//            if (nombre_archivo_pdf == null) {
//                JOptionPane.showMessageDialog(pn_abs, "El archivo pdf no existe... ", "!!!Error...", JOptionPane.ERROR_MESSAGE);
//            } else {
//                //si el nombre destino es null o vacio, se crea uno
//                nombre_destino = comprobarNombreDestino(pn_abstract.getNombreDestino(), nombre_archivo_pdf);
//                sin_cabecera = pn_abstract.getSinCabecera();
//                mc.writeFileCuadro(ruta_pdf, nombre_archivo_pdf, ruta_destino, nombre_destino, sin_cabecera);
//
//                total_datos = mc.getTotalDatosActualizar();
//                escrito = mc.getDatosEscritos();
//
//                if (escrito) {
//                    fi.setTxtMensaje("Fin del proceso de conversión cuadros...\n Se han escrito: " + total_datos + "lineas\n");
//                } else {
//                    fi.setTxtMensaje("Fin del proceso de conversión cuadros...\n No se han escrito datos...\n");
//                }
//
//                eliminarPdfLeido(ruta_pdf, nombre_archivo_pdf);
//            }
//        }
//    }
    
    
    /**
     * Crea el ManagerDiasGenerados y le envia los datos para actualizar los archivos y base de datos
     * @param pn_abstract 
     */
//    private void setGenerados(AbstractPanel pn_abstract) {
//        ManagerDiasGenerados md = new ManagerDiasGenerados();
//        String ruta_pdf = pn_abstract.getRutaPdf().getAbsolutePath();
//        String ruta_destino = pn_abstract.getRutaDestino().getAbsolutePath();
//        String nombre_archivo_pdf;
//        String nombre_destino = "";
//        boolean archivo_nuevo;
//        String total_datos;
//        boolean escrito;
//
//        //el nombre archivo_pdf debe existir
//        nombre_archivo_pdf = this.getNombrePdf(pn_abstract.getRutaArchivoPdf());
//        if (nombre_archivo_pdf == null) {
//            JOptionPane.showMessageDialog(pn_abs, "El archivo pdf no existe... ", "Error Init...", JOptionPane.ERROR_MESSAGE);
//        } else {
//            archivo_nuevo = pn_abstract.getArchivoNuevo();
//            //si archivo nuevo se necesita un nombre del archivo destino en caso que sea nulo o vacio
//            if (archivo_nuevo) {
//                nombre_destino = comprobarNombreDestino(pn_abstract.getNombreDestino(), nombre_archivo_pdf);
//            } else {
//                //al no ser un nuevo archivo es necesario que el archivo destino exista
//                File f = new File(ruta_destino + "\\" + nombre_destino);
//                if (!f.exists()) {
//                    JOptionPane.showMessageDialog(pn_abs, "Nombre archivo destino no existe... ", "Error Init...", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//            md.setFileDiasGenerados(ruta_pdf, nombre_archivo_pdf, ruta_destino, nombre_destino, archivo_nuevo);
//
//            total_datos = md.getTotalLineasEscritas();
//            escrito = md.getDatosEscritos();
//
//            if (escrito && archivo_nuevo) {
//                fi.setTxtMensaje("Fin del proceso de conversión dias generados...\n Se han escrito: " + total_datos + "lineas\n");
//            } else if (escrito && !archivo_nuevo) {
//                List<String> diferencias = md.getDiferencias();
//                fi.setTxtMensaje("Fin del proceso de conversión dias generados...\n");
//                for (String aux : diferencias) {
//                    fi.setTxtMensaje(aux + "\n");
//                }
//            } else {
//                fi.setTxtMensaje("Fin del proceso de conversión dias generados...\n No se han escrito datos...\n");
//            }
//            eliminarPdfLeido(ruta_pdf, nombre_archivo_pdf);
//        }
//    }
     
    
    /**
     * Crea el ManagerMinutos y le envia los datos necesarios
     * @param pn_abstract 
     */
//    private void setMinutos(AbstractPanel pn_abstract) {
//        ManagerMinutos mm = new ManagerMinutos();
//        String ruta_pdf = pn_abstract.getRutaPdf().getAbsolutePath();
//        String ruta_destino = pn_abstract.getRutaDestino().getAbsolutePath();
//        String nombre_archivo_pdf;
//        String nombre_destino;
//        String total_datos;
//        boolean escrito;
//
//        nombre_archivo_pdf = this.getNombrePdf(pn_abstract.getRutaArchivoPdf());
//        if (nombre_archivo_pdf == null) {
//            JOptionPane.showMessageDialog(pn_abs, "Nombre archivo pdf no existe... ", "Error Init...", JOptionPane.ERROR_MESSAGE);
//        } else {
//            File archivo_destino = new File(ruta_destino + "\\" + pn_abs.getNombreDestino());
//            nombre_destino = this.getNombrePdf(archivo_destino);
//            if (nombre_destino == null) {
//                JOptionPane.showMessageDialog(pn_abs, "Nombre archivo destino no existe... ", "Error Init...", JOptionPane.ERROR_MESSAGE);
//            } else {
//                mm.actualizarArchivo(ruta_pdf, nombre_archivo_pdf, ruta_destino, nombre_destino);
//
//                total_datos = mm.getTotalDatosActualizar();
//                escrito = mm.getDatosEscritos();
//
//                if (escrito) {
//                    fi.setTxtMensaje("Fin del proceso de conversión minutos...\n Se han escrito: " + total_datos + "lineas\n");
//                } else {
//                    fi.setTxtMensaje("Fin del proceso de conversión minutos...\n No se han escrito datos...\n");
//                }
//                eliminarPdfLeido(ruta_pdf, nombre_archivo_pdf);
//            }
//        }
//    }
    
    
    private void eliminarPdfLeido(String ruta_pdf, String nombre_pdf) {
        if (nombre_pdf == null) {
            int op = JOptionPane.showConfirmDialog(pn_abs, "Quiere eliminar todos los pdfs leidos...?", "Delete pdfs", JOptionPane.YES_NO_OPTION);
            if (op == 0) {
                eliminarTodosPdf(ruta_pdf);
            }
        } else if (!nombre_pdf.isEmpty() && nombre_pdf.contains(".pdf")) {
            int op = JOptionPane.showConfirmDialog(pn_abs, "Quiere eliminar pdf leido...?", "Delete pdf", JOptionPane.YES_NO_OPTION);
            if (op == 0) {
                eliminarPdf(ruta_pdf, nombre_pdf);
            }
        }
    }
    
    
    /**
     * Elimina el archivo si es un pdf
     * @param ruta_pdf
     * @param nombre_pdf 
     */
    private void eliminarPdf(String ruta_pdf, String nombre_pdf) {
        File f = new File(ruta_pdf + "\\" + nombre_pdf);
        f.delete();

        if (!f.exists()) {
            JOptionPane.showMessageDialog(pn_abs, " Eliminado el archivo: " + nombre_pdf, " Eliminar...", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(pn_abs, " El archivo " + nombre_pdf + " no ha sido eliminado...", " Eliminar...", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * Elimina todos los pdfs si el archivo es un directorio
     * @param files 
     */
    private void eliminarTodosPdf(String ruta_pdf) {
        File f = new File(ruta_pdf);
        int total_archivos_pdf = 0;
        int total_eliminados = 0;
        if (f.isDirectory()) {
            File[] archivos = f.listFiles();
            for (int a = 0; a < archivos.length; a++) {
                String name = archivos[a].getName();
                if (name.contains(".pdf")) {
                    total_archivos_pdf++;
                    archivos[a].delete();

                    if (!archivos[a].exists()) {
                        total_eliminados++;
                    }
                }
            }
            JOptionPane.showMessageDialog(pn_abs, " Eliminados: " + total_eliminados + " archivos de " + total_archivos_pdf + " archivos", "Eliminados...", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(pn_abs, "La ruta que quiere eliminar pdfs no es un directorio... ", "Aviso Init...", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    
    
    
    
    
    public static void main(String[] args) {
      new Init();
    }

}
