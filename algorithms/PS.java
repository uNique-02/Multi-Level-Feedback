package algorithms;

import javax.swing.JPanel;

import model.ProcessModel;

public class PS {
    
    JPanel boxPanel;
    ProcessModel process[];

    public PS (JPanel boxPanel, ProcessModel[] process) {
        this.boxPanel = boxPanel;
        this.process = process;
    }
}
