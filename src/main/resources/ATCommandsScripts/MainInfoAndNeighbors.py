import serial


def getStatus():
    ser = serial.Serial('COM6', 115200, timeout = 2)
    main_info = "AT+MONI\r"
    ber_rssi = "AT+CSQ\r" # ber rssi
    uplink_downlink= "AT+CPSI?\r"
    # cmd = "AT\r"
    ser.write(ber_rssi.encode())
    ser.write(main_info.encode())
    ser.write("AT+CNMP=13\r".encode())
    # ser.write(uplink_downlink.encode())
    response = ser.read(1024)
    print(response.decode('utf-8'))
    ser.close()
    return response.decode('utf-8')

# BER can only be detected and valid when a voice call is active. Or 99 will always be returned when no voice call ongoing.
# Here are some steps you can take to improve signal strength:
#
# 1. Place the modem at a different location without a lot of interference.
# 2. Use the USB extension cable provided in the FortiExtender package to connect the modem to the FortiExtender. Some modems are known to have better reception when connected with an extension cable
# 3. Turn the FortiExtender upside down from the current position. Signals might change when the antenna is facing a different direction
# 4. Verify there are no local storms or other weather conditions that can affect the reception
getStatus()