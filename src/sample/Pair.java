package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Pair {
    private SimpleIntegerProperty no=new SimpleIntegerProperty();
    private SimpleStringProperty word=new SimpleStringProperty();
    private SimpleIntegerProperty count=new SimpleIntegerProperty();

    public Pair(String word, int count) {
        //this.no.setValue();
        this.word.setValue(word);
        this.count.setValue(count);
    }

    public String getWord() {
        return word.getValue();
    }

    public int getCount() {
        return count.getValue();
    }
}
