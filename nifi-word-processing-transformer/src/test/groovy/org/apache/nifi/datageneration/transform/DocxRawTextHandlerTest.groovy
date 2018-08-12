package org.apache.nifi.datageneration.transform

import org.apache.nifi.datageneration.transform.docx.DocxRawTextHandler
import org.junit.Test

class DocxRawTextHandlerTest extends TestBase {
    @Test
    void test() {
        def handler = new DocxRawTextHandler()
        def text = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc et eros elementum, consequat odio in, fringilla augue. Suspendisse imperdiet, risus ultrices feugiat ornare, nunc nibh tristique neque, eget condimentum ligula sapien at augue. Sed malesuada sit amet diam vel mollis. Vivamus posuere ante at ligula malesuada imperdiet. Quisque eros nisi, elementum eu mauris sed, cursus ultricies augue. Aliquam odio justo, mattis id nisl id, sodales pulvinar nibh. Donec commodo rutrum justo, vel vestibulum arcu semper id. Morbi orci sem, efficitur vitae enim sed, blandit venenatis ex. Integer consequat arcu vel urna scelerisque feugiat. Proin iaculis lacus id dui placerat cursus. Vivamus eu odio vehicula, tincidunt enim quis, rutrum massa. Curabitur varius orci vitae nibh sagittis, vitae vehicula tortor suscipit. Nam viverra, urna sit amet congue molestie, urna urna mollis justo, ut volutpat felis velit non nisl. Nunc sit amet nisi vitae erat porttitor tristique. Mauris sed enim eleifend, ullamcorper eros eu, elementum massa.\n
Maecenas efficitur sapien sed ex semper luctus ac a urna. Nunc placerat at neque at laoreet. Curabitur eu metus tempus, accumsan eros eu, vestibulum lorem. Cras tincidunt, arcu nec tincidunt aliquet, nunc felis aliquet libero, non semper mi purus non orci. In hac habitasse platea dictumst. Donec sagittis libero eu varius aliquam. Fusce lobortis euismod libero sit amet porta. Suspendisse laoreet odio eleifend iaculis pellentesque.\n
Mauris at libero id leo iaculis tristique. Curabitur quis efficitur turpis. Phasellus pretium nibh nec facilisis molestie. Donec eu pellentesque justo. Vestibulum quis dui ut erat finibus iaculis. In a semper erat. In viverra, nunc in gravida commodo, dui dui mattis leo, sit amet sodales lacus erat nec dolor.\n
Vivamus sapien nulla, rhoncus a nisl ac, volutpat dignissim augue. Aliquam quis aliquam nisi. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce euismod viverra ultrices. Curabitur molestie lorem ac dolor convallis, a sagittis purus posuere. Cras in diam non purus varius euismod. Etiam at leo viverra, lobortis elit in, porta lorem. Sed hendrerit sapien eget diam molestie, in ultricies massa tincidunt. Praesent porttitor pretium ligula, eget hendrerit libero placerat at. Nunc a rhoncus tortor. Phasellus finibus fringilla consectetur.\n
Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed maximus tempor volutpat. Phasellus ut finibus sapien, ac lacinia lectus. Morbi ultrices turpis sit amet maximus accumsan. Aliquam ipsum urna, vestibulum ac lacus vel, dictum molestie nisl. Cras ut dui quis orci lacinia placerat. Curabitur eleifend felis lacus, sit amet luctus augue vulputate ut. Maecenas ac dolor pulvinar, auctor orci et, ornare nisl. Donec suscipit, erat eu volutpat viverra, tortor felis consectetur dui, ac maximus leo felis in mi. Morbi id nisi eu ante sagittis hendrerit et in augue. Donec at lorem magna. In bibendum varius scelerisque. Integer ac laoreet dolor. Ut commodo neque enim, sit amet feugiat dui congue vitae. Suspendisse maximus aliquam tortor, ut luctus augue aliquam eu. 
        """.bytes

        def results = handler.transform(text, [:])
        dumpFile(results, "basic.docx")
    }
}
