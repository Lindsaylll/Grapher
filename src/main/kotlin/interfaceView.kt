import javafx.beans.Observable
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javax.swing.GroupLayout



class interfaceView(
    private val model: Model,
    private val controller: Main
) : BorderPane(), IView {
    override fun updateView() {
        // abstracting data for current dataset
        val name = model.curData
        curdata = model.allDataSets[model.findSet(name)]
        //update title
        title.text =curdata.title
        //update xAxis
        x_name.text = curdata.xAxis
        //update yAxis
        y_name1.text = curdata.yAxis
        // update view
        center = CanvasView(model, controller)
        var m = findmax(curdata.data)
        // update max of y-axis
        max.text = "$m"
    }


    var curdata = model.allDataSets[model.get_curDataIdx()]
    val max = Label()
    var title = Label("${curdata!!.title}")
    var  x_name =  Label("${curdata!!.xAxis}")
    var y_name1 =  Text("${curdata!!.yAxis}")

    // helper function to find max as integer
    private fun findmax(data: MutableList<Int>):Int {
        var maxhei = 0
        for(item in data){
            if(item > maxhei){
                maxhei = item
            }
        }
        return maxhei
    }


    init{
        //layout
        background = simpleFill(Color.WHITE)
        title.textFill = Color.BLACK

        // title
        val toptmp = HBox(title)
        toptmp.alignment = Pos.CENTER
        toptmp.prefHeight = 50.0
        top = toptmp

        // x-axis
        val bottmp = HBox(x_name)
        bottmp.alignment = Pos.CENTER
//        bottmp.padding = Insets(15.0)
        bottmp.prefHeight = 50.0
        bottom = bottmp

        // y-axis
        val lefttmp = BorderPane()
        lefttmp.prefWidth = 50.0
        // max value
        val maxhei = findmax(curdata!!.data).toInt()
        max.text = "$maxhei"
        max.prefWidth = 50.0
        max.alignment = Pos.TOP_RIGHT
        max.translateY = -10.0
        // 0
        val min = Label("0")
        min.prefWidth = 50.0
        min.alignment = Pos.BOTTOM_RIGHT
        min.translateY = 10.0
        // y-axis name
        y_name1.minHeight(50.0)
        y_name1.rotate = -90.0
        //layout
        lefttmp.top = max
        lefttmp.bottom = min
        lefttmp.center = y_name1
        lefttmp.maxWidth = 50.0
        left = lefttmp

        // draw bar graph
        center = CanvasView(model, controller)

        // make left and right space besides the bra graph even
        val rttmp = VBox()
        rttmp.prefWidth = 40.0
        rttmp.isVisible  = false
        right = rttmp

        // register with the model when we're ready to start receiving data
        model.addView(this)
    }

    // this pane is Resizable
    override fun isResizable(): Boolean {
        return true
    }
}