import serial


def getStatus():
    ser = serial.Serial('COM6', 115200, timeout = 2)
    main_info = "AT+MONI\r"
    ber_rssi = "AT+CSQ\r" # ber rssi
    ser.write(ber_rssi.encode())
    ser.write(main_info.encode())
    response = ser.read(1024)
    print(response.decode('utf-8'))
    ser.close()
    return response.decode('utf-8')

getStatus()