/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.olympiad.managedbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import ru.olympiad.ejb.OlympiadEJB;
import ru.olympiad.entities.Resource;
import ru.olympiad.entities.Task;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class TaskEditor {
    @EJB
    private OlympiadEJB olympiadEJB;
    private UploadedFile uploadedFile;
    private boolean newTask;
    private Task task;
    
    public void listener() {
        if (task == null) {
            Map<String, String> params = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap();
            String idString = params.get("taskid");
            if (idString != null) {
                if (idString.equals("new")) {
                    task = new Task();
                    task.setResourceList(new ArrayList<Resource>());
                    newTask = true;
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(idString);
                    task = olympiadEJB.getTask(id);
                } catch (Exception ex) {
                }
            }
        }
    }
    
    public String saveTask(){
        if (uploadedFile != null) {
//            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
//                    .getExternalContext().getContext();
//            String resourcesPath = ctx.getRealPath("/resources/files/taskresources/");
            String resourcesPath = "d:/alex/workspace/files/taskresources/";
            String prefix = FilenameUtils.getBaseName(uploadedFile.getName());
            String suffix = FilenameUtils.getExtension(uploadedFile.getName());
            File file = null;
            OutputStream output = null;
            try {
                file = File.createTempFile(prefix + "_", "." + suffix, new File(resourcesPath));
                output = new FileOutputStream(file);
                IOUtils.copy(uploadedFile.getInputStream(), output);
                Resource resource = new Resource();
                resource.setTaskId(task);
                resource.setResPath("taskresources/" + file.getName());
                task.getResourceList().add(resource);
            } catch (IOException ex) {
                if (file != null) {
                    file.delete();
                }
                Logger.getLogger(TaskEditor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                IOUtils.closeQuietly(output);
            }
        }
        olympiadEJB.saveTask(task, newTask);
        if (newTask){newTask = false;}
        return "taskeditor?taskid=" + task.getId();
    }
    
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
}
