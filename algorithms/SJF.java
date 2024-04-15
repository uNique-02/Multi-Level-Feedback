package algorithms;

import javax.swing.JPanel;

import model.ProcessModel;

public class SJF {

    JPanel boxPanel;
    ProcessModel process[];

    public SJF (JPanel boxPanel, ProcessModel[] process) {
        this.boxPanel = boxPanel;
        this.process = process;
    }
    
    
}
