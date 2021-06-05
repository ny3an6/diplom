import serial


def getStatus():
    ser = serial.Serial('COM6', 115200, timeout = 2)
    cmd = "AT+CGSETI?\r"
    cmd = "AT+MONI\r"
    ser.write(cmd.encode())
    # response = ser.read(2)
    response = ser.read(1024)
    print(response.decode('utf-8'))
    # print(type(response.decode('utf-8')))
    ser.close()
    return response.decode('utf-8')

getStatus()