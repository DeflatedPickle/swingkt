import com.deflatedpickle.swingkt.api.Compass
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.impl.Model
import com.deflatedpickle.swingkt.widget.swing.spinner
import com.deflatedpickle.swingkt.widget.swingx.button
import com.deflatedpickle.swingkt.widget.swingx.frame
import com.deflatedpickle.swingkt.widget.swingx.label
import com.deflatedpickle.swingkt.widget.swingx.panel
import java.util.Date

fun main() {
    frame(Layout.Flow()) {
        title = "frame"
        width = 420
        height = 360

        components {
            panel(Layout.Border(5, 5), Constraint.Flow()) {
                components {
                    button(Constraint.Border(Compass.EAST)) {
                        text = "ok"

                        onClick += Listener {
                            println("hello")
                        }
                    }

                    label(Constraint.Border(Compass.WEST)) {
                        text = "press"
                    }

                    spinner(Constraint.Border(Compass.ROSE)) {
                        model = Model.Spinner.Number<Double> {
                            value = 8.0
                            min = -2.0
                            max = 24.0
                            step = 0.2
                        }

                        onChange += Listener {
                            println("it has changed")
                        }
                    }
                }
            }
        }
    }.show()
}
