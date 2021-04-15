/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;

//import javax.enterprise.context.Dependent;
//import javax.faces.bean.RequestScoped;
/**
 *
 * @author lenine
 */

//@Named(value = "navigationController")
@ManagedBean(name = "navigationController")
//@RequestScoped
//@Dependent
@RequestScoped
public class NavigationController implements Serializable {
    
//    public String tela = "dashboard";
    
    public NavigationController() {
    }
    
//    public void telaProfissional(){
//        tela = "profissional";
//    }
//    
//    public boolean isProfissional(){
//        return "profissional".equals(tela);
//    }
//    
//    public void telaDashboard(){
//        tela = "dashboard";
//    }
//    
//    public boolean isDashboard(){
//        return "dashboard".equals(tela);
//    }
    
    public String home(){
        return "index?faces-redirect=true";
    }
    public String profissional(){
        return "/profissional?faces-redirect=true";
    }
    
    public String subcontrato(){
        return "/subcontrato?faces-redirect=true";
    }
    
    public String usuario(){
        return "/usuario/List?faces-redirect=true";
    }
    
    public String cliente(){
        return "/cliente/List?faces-redirect=true";
    }
    
    public String rdcEfetivo(){
        return "/rdcEfetivo/List?faces-redirect=true";
    }
}
