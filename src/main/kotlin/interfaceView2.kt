import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Text

class interfaceView2(
    private val model: Model,
    private val controller: Main
) : BorderPane(), IView {
    override fun updateView() {
        val name = model.curData
        val data = model.allDataSets[model.findSet(name)]
        curdata = data
        title.text =data.title
        x_name.text = data.xAxis
        y_name1.text = data.yAxis
        center = PieView(model, controller)
        var m = findmax(data.data)
        max.text = "$m"


    }


    var curdata = model.allDataSets[model.get_curDataIdx()]

//    var dataset = createTestDataSet(model.curData)
//    var title = Label("${dataset!!.title}")
//    var x_name =  Label("${dataset!!.xAxis}")
//    var y_name =  Label("${dataset!!.yAxis}")


    private fun findmax(data: MutableList<Int>):Double {
        var maxhei = 0.0
        for(item in data){
            if(item > maxhei){
                maxhei = item.toDouble()
            }
        }
        return maxhei
    }

    //    var ht = height
    val max = Label()
    var title = Label("${curdata!!.title}")
    var  x_name =  Label("${curdata!!.xAxis}")
    var y_name1 =  Text("${curdata!!.yAxis}")
//    var y_name2 =  Label("${curdata!!.yAxis}")

    init{
//        ht = height

        background = simpleFill(Color.WHITE)
        title.textFill = Color.BLACK

        val toptmp = HBox(title)
        toptmp.alignment = Pos.CENTER
        toptmp.prefHeight = 50.0
//        toptmp.padding = Insets(15.0)
        top = toptmp

        val bottmp = HBox(x_name)
        bottmp.alignment = Pos.CENTER
//        bottmp.padding = Insets(15.0)
        bottmp.prefHeight = 50.0
        bottom = bottmp

        val lefttmp = BorderPane()
        //lefttmp.alignment = Pos.CENTER
//        lefttmp.padding = Insets(15.0)
//        lefttmp.prefHeight = ht
        lefttmp.prefWidth = 50.0

        // lefttmp.isFillWidth = true
        val maxhei = findmax(curdata!!.data).toInt()
        max.text = "$maxhei"
        max.prefWidth = 50.0
        max.alignment = Pos.TOP_RIGHT
        max.translateY = -10.0
        val min = Label("0")
        min.prefWidth = 50.0
        min.alignment = Pos.BOTTOM_RIGHT
        min.translateY = 10.0
        y_name1.minHeight(50.0)
        y_name1.rotate = -90.0

//        y_name1.wrappingWidth = 50.0
//        y_name1.maxWidth(50.0)
//        y_name1.alignment = Pos.CENTER_RIGHT
//        y_name1.isWrapText = false
//        y_name1.minWidth = 50.0
//        println("here is yname: ${y_name1.wrappingWidth }")
        lefttmp.top = max
        lefttmp.bottom = min
        lefttmp.center = y_name1
        lefttmp.maxWidth = 50.0
        left = lefttmp

//        var tryit  = VBox()
//        tryit.top


//        val cter = VBox()
//        cter.border = Border(BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null,BorderWidths(1.0)))
        center = PieView(model, controller)



        val rttmp = VBox()
//        rttmp.padding = Insets(15.0)
        rttmp.prefWidth = 40.0
        rttmp.isVisible  = false
        right = rttmp


        //getChildren().add(canvas)
        model.addView(this)



    }

    override fun isResizable(): Boolean {
        return true
    }

//    override fun prefWidth(height: Double): Double {
//        return width
//    }
//
//    override fun prefHeight(width: Double): Double {
//        return height
//    }

}