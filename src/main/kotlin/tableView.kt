import javafx.beans.value.ObservableValue
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.layout.*
import javafx.scene.paint.Color

class tableView(
    private val model: Model,
    private val controller: Main
) :GridPane(){
    init{
        // the index/id of each spinner
        var i = 0
        // abstracting data of the current dataset
        var myData = model.allDataSets[model.get_curDataIdx()]
        // create spinners for all data point of the current dataset
        for(item in myData!!.data){
            // label show spinner's index
            val x = Label("${i+1}:     ")
            val s: Spinner<*> = Spinner<Any?>(1, 100, item);
            // make each spinner unique by recording its index
            s.id = i.toString()
            // change of spinner will cause modifying dataset and graph
            s.valueProperty().addListener { obj, old, new ->
                val theidx = s.id.toInt()
                debug("id is: $theidx")
                debug("$new")
                model.modify(model.curData, theidx, new as Int)
            }
            // layout
            x.prefWidth = 35.0
            x.alignment = Pos.CENTER_RIGHT
            s.prefWidth = 80.0
            this.add(x, 0,i,1,1);
            this.add(s, 1,i);
            ++i
        }
        // layout
        alignment = Pos.CENTER_LEFT
        padding = Insets(10.0)
        prefWidth = 150.0
        maxWidth = 150.0
        border = Border(BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null,BorderWidths(1.0)))
    }
}
