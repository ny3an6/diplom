import serial


def getStatus():
    ser = serial.Serial('COM6', 115200, timeout = 1)
    # cmd = "AT+CPSI?\r"
    cmd = "AT+COPS=5\r"
    ser.write(cmd.encode())
    # response = ser.read(2)
    response = ser.read(64)
    print(response.decode('utf-8'))
    # print(type(response.decode('utf-8')))
    ser.close()
    return response.decode('utf-8')

getStatus()
