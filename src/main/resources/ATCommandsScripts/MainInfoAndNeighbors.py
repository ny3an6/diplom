import serial


def getStatus():
    ser = serial.Serial('COM6', 115200, timeout = 2)
    cmd = "AT+MONI\r"
    ser.write(cmd.encode())
    response = ser.read(1024)
    print(response.decode('utf-8'))
    ser.close()
    return response.decode('utf-8')

getStatus()