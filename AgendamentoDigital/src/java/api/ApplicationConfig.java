/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author mathm
 */
@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(api.restAgendamento.class);
        resources.add(api.restCategoria.class);
        resources.add(api.restCategoriaServico.class);
        resources.add(api.restEmpresa.class);
        resources.add(api.restEmail.class);
        resources.add(api.restFuncionario.class);
        resources.add(api.restPessoa.class);
        resources.add(api.restRelatorioServico.class);
        resources.add(api.restServico.class);
        resources.add(api.restUsuario.class);
    }
}
