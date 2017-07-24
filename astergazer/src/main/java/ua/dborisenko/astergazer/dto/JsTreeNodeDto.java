package ua.dborisenko.astergazer.dto;

import java.util.HashMap;
import java.util.Map;

import ua.dborisenko.astergazer.domain.Context;
import ua.dborisenko.astergazer.domain.Extension;
import ua.dborisenko.astergazer.domain.Script;

public class JsTreeNodeDto {

    private static final String SCRIPT_NODE_TYPE = "script";
    private static final String EXTENSION_NODE_TYPE = "extension";
    private static final String CONTEXT_NODE_TYPE = "context";

    private String id;

    private String parent;

    private String text;

    private String type;

    private Map<String, String> data = new HashMap<>();

    public JsTreeNodeDto(Context context) {
        this.id = CONTEXT_NODE_TYPE + context.getId();
        this.parent = "#";
        this.text = context.getName();
        this.type = CONTEXT_NODE_TYPE;
        this.data.put("id", String.valueOf(context.getId()));
    }

    public JsTreeNodeDto(Extension extension) {
        this.id = EXTENSION_NODE_TYPE + extension.getId();
        this.parent = CONTEXT_NODE_TYPE + extension.getContext().getId();
        this.text = extension.getName();
        Script script = extension.getScript();
        this.data.put("id", String.valueOf(extension.getId()));
        this.data.put("contextId", String.valueOf(extension.getContext().getId()));
        if (script != null) {
            this.data.put("scriptId", String.valueOf(script.getId()));
            this.data.put("scriptName", script.getName());
        }
        this.type = EXTENSION_NODE_TYPE;
    }

    public JsTreeNodeDto(Script script) {
        this.id = SCRIPT_NODE_TYPE + script.getId();
        this.parent = "#";
        this.text = script.getName();
        this.type = SCRIPT_NODE_TYPE;
        this.data.put("id", String.valueOf(script.getId()));
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

}
