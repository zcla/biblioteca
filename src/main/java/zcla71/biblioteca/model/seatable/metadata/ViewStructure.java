package zcla71.biblioteca.model.seatable.metadata;

import java.util.Collection;

public class ViewStructure {
    private Collection<Object> folders; // TODO Object -> class
    private Collection<String> view_ids;

    public Collection<Object> getFolders() {
        return folders;
    }

    public void setFolders(Collection<Object> folders) {
        this.folders = folders;
    }

    public Collection<String> getView_ids() {
        return view_ids;
    }

    public void setView_ids(Collection<String> view_ids) {
        this.view_ids = view_ids;
    }
}
