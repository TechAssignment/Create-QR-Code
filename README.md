# Create-QR-Code
Create QR Code is an application that allows user to create QR Codes for any text they enter.

This application uses https://api.qrserver.com webapplication to generate QR Codes and to fetch those QR Codes.

Requirements for the app:-

1. The app should start with a screen displaying a list, the list will be used to display QR codes. 
2. The list items will have the QR code image on the left, and the data stored inside the QR code on the right. 
3. The QR codes are generated and returned from a remote web server provided by qrserver.com. e.g: 
    https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=Bikrant  
4. A timer will be used to add a new QR code to the list, with an interval set to fire every 10 seconds.
5. When the timer fires, retrieve a QR code image from the remote server using random text, and thus create a new        list item.
6. [Not Complete, Will be doing on Jan 31, 2015] Clicking on a QR code list item, should allow user to update or delete the QR code. Which would refresh the respective QR code the user changed.
7. User should be able to create a QR code by supplying their own data.


Libraries Used in this Application:-

1. AppCompat
2. RecyclerView
3. Picasso
4. FloatingActionButton
5. DividerDecoration
