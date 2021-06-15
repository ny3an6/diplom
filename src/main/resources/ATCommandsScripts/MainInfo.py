import serial


def getStatus():
    ser = serial.Serial('COM6', 115200, timeout = 2)

    cmd = "AT+CSQ?\r"
    cmd = "AT+CGSETI?\r"
    cmd = "AT+CCINFO\r"
    uplink_downlink_conf = "AT+CNMP=13\r"
    cmd = "AT+CPSI?\r"
    # cmd = "AT+MONI\r"
    # cmd = "AT+CLAC\r"
    # cmd = "AT+CMGSI\r"
    # ser.write(uplink_downlink_conf.encode())
    ser.write(cmd.encode())
    ser.write("AT+CGDCONT?\r".encode())
    # response = ser.read(2)
    response = ser.read(1024)
    print(response.decode('utf-8'))
    # print(type(response.decode('utf-8')))
    ser.close()
    return response.decode('utf-8')

getStatus()
