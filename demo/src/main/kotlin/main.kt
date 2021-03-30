import com.deflatedpickle.swingkt.api.Compass
import com.deflatedpickle.swingkt.impl.Constraint
import com.deflatedpickle.swingkt.impl.Layout
import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.button
import com.deflatedpickle.swingkt.frame
import com.deflatedpickle.swingkt.label
import com.deflatedpickle.swingkt.panel

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
                }
            }
        }
    }.show()
}
