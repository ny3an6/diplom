import serial


def getStatus():
    ser = serial.Serial('/dev/ttyS0', 115200, timeout = 2)
    cmd = "ATA\r"
    ser.write(cmd.encode())
    # response = ser.read(2)
    response = ser.read(64)
    print(response.decode('utf-8'))
    ser.close()

getStatus()
# print("Hello Baeldung Readers!!!")
