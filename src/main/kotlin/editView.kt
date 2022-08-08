import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox


class editView(
    private val model: Model,
    private val controller: Main
) : HBox(), IView {
    override fun updateView() {
        // show new dataset's new title and new x-axis and new y-axis
        myData = model.allDataSets[model.get_curDataIdx()]
        titleField.text = myData.title
        xField.text =myData.xAxis
        yField.text = myData.yAxis
    }

    // title
    val titleField = TextField("")
    // x-axis
    val xField = TextField("")
    // y-axis
    val yField = TextField("")
    // current showing dataset
    var myData = model.allDataSets[model.get_curDataIdx()]

    init {
        // add listener to x-axis Field,y-axis Field,titleField
        // once it's edited, the change will show up on edit field and graph
        xField.textProperty()
            .addListener { observable, oldValue, newValue ->  model.updateName(model.get_curDataIdx(), "X",xField.text) }
        yField.textProperty()
            .addListener {observable, oldValue, newValue ->model.updateName(model.get_curDataIdx(), "Y",yField.text) }
        titleField.textProperty()
            .addListener { observable, oldValue, newValue -> model.updateName(model.get_curDataIdx(), "title",titleField.text) }

        //abstracting the data of current showing dataset
        myData = model.allDataSets[model.get_curDataIdx()]
        prefHeight = 30.0
        padding = Insets(10.0)
        spacing = 8.0

        // make three labels show at the correct position
        val tt = Label("Title:")
        tt.isWrapText = false
        tt.translateY = 5.0
        val xa = Label("X-Axis:")
        xa.isWrapText = false
        xa.translateY = 5.0
        val ya = Label("Y-Axis:")
        ya.translateY = 5.0
        ya.isWrapText = false
        titleField.prefWidth = 150.0
        xField.prefWidth = 150.0
        yField.prefWidth = 150.0

        // let the three text field show correct content
        titleField.text = myData.title
        xField.text =myData.xAxis
        yField.text = myData.yAxis

        //add them to pane
        children.addAll(tt, titleField, xa, xField, ya, yField)
        model.addView(this)
    }
}
