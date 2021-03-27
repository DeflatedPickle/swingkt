import com.deflatedpickle.swingkt.api.Listener
import com.deflatedpickle.swingkt.button
import com.deflatedpickle.swingkt.frame
import com.deflatedpickle.swingkt.panel

fun main() {
    frame {
        title = "frame"
        width = 420
        height = 360

        components {
            panel {
                components {
                    button {
                        text = "click"

                        onClick += Listener {
                            println("hello")
                        }
                    }
                }
            }
        }
    }.show()
}