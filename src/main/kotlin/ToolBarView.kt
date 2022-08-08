import javafx.beans.value.ObservableValue
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.*
import java.awt.SystemColor.text


class ToolBarView(
    private val model: Model,
    private val controller: Main
) : ToolBar(), IView {
    override fun updateView() {
    }

    init{
        padding = Insets(10.0)
        prefHeight = 30.0

        val ds = Label("Dataset:")
        ds.prefWidth = 50.0
        println("DBG: add")

        // choice box for choosing datset
        val cb: ChoiceBox<String> = ChoiceBox()
        cb.prefWidth = 100.0
        cb.getItems().addAll("Increasing", "Large", "Middle", "Single", "Range", "Percentage");
        cb.onAction=  EventHandler { event: ActionEvent ->
            controller.changeDataSet(cb.value)
        }
        // ask choice box to show the current showing dataset's name
        cb.value = model.curData

        // separator bar
        val sp1 = Separator(Orientation.VERTICAL)
        sp1.setPrefWidth(30.0)

        // button for adding new dataset
        val newBt = Button("New")
        newBt.prefWidth= 80.0
        newBt.maxWidth = 80.0
        newBt.onMouseClicked = EventHandler {
            controller.newData()
            cb.getItems().add(model.curData)
            cb.value = model.curData
        }

        // to choose how many data points about to create for new adding dataset
        val spinner: Spinner<*> = Spinner<Any?>(1, 20, 5)
        spinner.setPrefWidth(60.0)
        spinner.valueProperty().addListener { obj, old, new ->
            model.creatHowMany = new as Int
        }

        // to choose which graph to show
        val cb2: ChoiceBox<String> = ChoiceBox()
        cb2.prefWidth = 100.0
        cb2.getItems().addAll("Bar Graph", "Pie Chart");
        cb2.onAction=  EventHandler { event: ActionEvent ->
            if(cb2.value == "Bar Graph") {model.showpiechart = false;controller.swichScene(false)}
            if(cb2.value == "Pie Chart") {model.showpiechart = true;controller.swichScene(true)}
            println("${cb2.value}")
        }
        cb2.value = "Bar Graph"


        // seperator
        val sp2 = Separator(Orientation.VERTICAL)
        sp2.setPrefWidth(30.0)

        items.addAll(ds, cb,sp1, newBt, spinner,sp2,cb2)
        model.addView(this)
    }
}
