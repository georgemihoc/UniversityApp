package utils.events;


import validators.Nota;
import validators.Student;
import validators.Tema;

public class ChangeEvent implements Event {
    private ChangeEventType type;
    private Student data, oldData;
    private Nota dataN,oldDataN;
    private Tema dataT, oldDataT;

    public ChangeEvent(ChangeEventType type, Student data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEvent(ChangeEventType type, Nota dataN) {
        this.type = type;
        this.dataN = dataN;
    }

    public ChangeEvent(ChangeEventType type, Nota dataN, Nota oldDataN) {
        this.type = type;
        this.dataN = dataN;
        this.oldDataN = oldDataN;
    }

    public ChangeEvent(ChangeEventType type, Student data, Student oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEvent(ChangeEventType add, Tema t) {
        this.type = add;
        this.dataT = t;
    }

    public ChangeEvent(ChangeEventType update, Tema t, Tema oldTask) {
        this.type = update;
        this.dataT = t;
        this.oldDataT = oldTask;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Student getData() {
        return data;
    }

    public Student getOldData() {
        return oldData;
    }
}