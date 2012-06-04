/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ucb.caracol.view;

import javax.swing.JOptionPane;


public class View {
    
    public static void showMsg(String msg){
        JOptionPane.showMessageDialog(null, msg, "Aviso do Sistema",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showMsgErro(String msg){
        JOptionPane.showMessageDialog(null, msg, "Aviso do Sistema",JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showFeedBack(String msg){
        JanelaPrincipal.setTxt_feedBack(JanelaPrincipal.getTxt_feedBack().getText()+msg);
    }
    
    
}
