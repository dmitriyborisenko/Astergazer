package ua.dborisenko.astergazer.dto;

import java.util.HashMap;
import java.util.Map;

import ua.dborisenko.astergazer.domain.Checklist;
import ua.dborisenko.astergazer.domain.ChecklistEntry;

public class JsTreeNodeDynamicDto {

    private static final String CHECKLIST_NODE_TYPE = "checklist";
    
    private static final String CHECKLIST_ENTRY_NODE_TYPE = "entry";
    
    private String id;
    
    private Boolean children;
    
    private String parent;
    
    private String text;
    
    private Map<String, String> data = new HashMap<>();
    
    private String type;
  
    public JsTreeNodeDynamicDto(Checklist checkList) {
        this.id = CHECKLIST_NODE_TYPE + checkList.getId();
        this.children = true;
        this.parent = "#";
        this.text = checkList.getName();
        this.data.put("id", String.valueOf(checkList.getId()));
        this.type = CHECKLIST_NODE_TYPE;
    }

    public JsTreeNodeDynamicDto(ChecklistEntry entry) {
        this.id = CHECKLIST_ENTRY_NODE_TYPE + entry.getId();
        this.children = false;
        this.parent = CHECKLIST_NODE_TYPE + entry.getChecklist().getId();
        this.text = entry.getControlValue();
        this.data.put("returnValue", entry.getReturnValue());
        this.data.put("id", String.valueOf(entry.getId()));
        this.data.put("checklistId", String.valueOf(entry.getChecklist().getId()));
        this.type = CHECKLIST_ENTRY_NODE_TYPE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Boolean getChildren() {
        return children;
    }

    public void setChildren(Boolean children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    
}
