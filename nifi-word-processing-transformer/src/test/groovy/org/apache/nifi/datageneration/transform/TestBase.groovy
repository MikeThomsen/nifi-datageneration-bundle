package org.apache.nifi.datageneration.transform

class TestBase {
    def dumpFile(byte[] data, String fileName) {
        def file = new File("/tmp/${fileName}")
        def out = new FileOutputStream(file)
        out.write(data)
        out.close()
    }
}
