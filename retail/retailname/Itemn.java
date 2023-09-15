import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/widgets.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:mailer/smtp_server/gmail.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:io';
import 'package:mailer/mailer.dart';

class Itemn {
    final String name;
    final String description;
    final String price;
    final String imgurl;
    Itemn(this.name, this.description, this.price, this.imgurl);
}

class Search {
    final String input;
    Search(this.input);
}

class Checkout {
    String item;
    String price;
    Checkout(this.item, this.price);
}

    _write(String text) async {
        final Directory directory = await getApplicationDocumentsDirectory();
        final File file = File('${directory.path}/my_file.txt');
        await file.writeAsString(text);
    }

    _cart_write(String text) async {
        final Directory directory = await getApplicationDocumentsDirectory();
        final File file = File('${directory.path}/cart.txt');
        await file.writeAsString(text);
    }

    _wishlist_write(String text) async {
        final Directory directory = await getApplicationDocumentsDirectory();
        final File file = File('${directory.path}/wish.txt');
        await file.writeAsString(text);
    }

    email(String email, String text) async {
        String username = "retailstoreprojects@gmail.com"; //Your Email;
        String password = "dpse@2020"; //Your Email's password;

        final smtpServer = gmail(username, password);
        // Creating the Gmail server

        // Create our email message.
        final message = Message()
                ..from = Address(username)
                ..recipients.add(email) //recipent email
                ..subject =
                'Your Order has been successfully placed. Thank you for shopping with RetailName' //subject of the email
                        ..text = text //body of the email
        ;
        try {
            final sendReport = await send(message, smtpServer);
            print(
                    'Message sent: ' + sendReport.toString()); //print if the email is sent
        } on MailerException catch (e) {
                print(
                        'Message not sent. \n' + e.toString()); //print if the email is not sent
        // e.toString() will show why the email is not sending
  }
    }

    Future<String> _read() async {
        String text;
        try {
            final Directory directory = await getApplicationDocumentsDirectory();
            final File file = File('${directory.path}/my_file.txt');
            text = await file.readAsString();
            return text;
        } catch (e) {
            return '';
        }
    }

    Future<String> _cart_read() async {
        String text;
        try {
            final Directory directory = await getApplicationDocumentsDirectory();
            final File file = File('${directory.path}/cart.txt');
            text = await file.readAsString();
            return text;
        } catch (e) {
            return '';
        }
    }

    Future<String> _wishlist_read() async {
        String text;
        try {
            final Directory directory = await getApplicationDocumentsDirectory();
            final File file = File('${directory.path}/wish.txt');
            text = await file.readAsString();
            return text;
        } catch (e) {
            return '';
        }
    }

    void main() => runApp(MaterialApp(
        home: Login(),
        ));

class MyApp extends StatefulWidget {
    @override
    _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
    var input = TextEditingController();
    List<Itemn> litems = [
    Itemn('Cauliflower', 'cauliflower bla bla', 'Rs.127',
                  'https://d2ebzu6go672f3.cloudfront.net/media/content/images/p7_Cauliflower_HH1810_gi887543438.jpg'),
    Itemn('Cauliflower', 'cauliflower bla bla', 'Rs.127',
                  'https://d2ebzu6go672f3.cloudfront.net/media/content/images/p7_Cauliflower_HH1810_gi887543438.jpg'),
    Itemn('Cauliflower', 'cauliflower bla bla', 'Rs.127',
                  'https://d2ebzu6go672f3.cloudfront.net/media/content/images/p7_Cauliflower_HH1810_gi887543438.jpg')
  ];
    String read = '';
    _MyAppState() {
        _read().then((val) => setState(() {
            read = val;
        }));
    }
    @override
    Widget build(BuildContext context) {
        String user = read.split(';')[0].split(' ')[0];
        return Scaffold(
                bottomNavigationBar: BottomAppBar(
                color: Colors.white,
                child: Padding(
                padding: const EdgeInsets.symmetric(vertical: 15.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
        IconButton(
                icon: Icon(
                Icons.home,
                color: Colors.orangeAccent,
                size: 40.0,
                ),
              ),
        IconButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => wishlist()));
        },
        icon: Icon(
                Icons.favorite,
                size: 40.0,
                ),
              ),
        IconButton(
                onPressed: () {
            Navigator.push(
                    context, MaterialPageRoute(builder: (context) => cart()));
        },
        icon: Icon(
                Icons.shopping_cart,
                size: 40.0,
                ),
              ),
        IconButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => account()));
        },
        icon: Icon(
                Icons.account_circle,
                size: 40.0,
                ),
              ),
            ],
          ),
        ),
      ),
        body: SafeArea(
                child: SingleChildScrollView(
                child: Column(
                children: <Widget>[
        Container(
                height: 165,
                child: Stack(children: <Widget>[
        Container(
                decoration: BoxDecoration(
                gradient: LinearGradient(
                colors: [Colors.orangeAccent, Colors.yellow[500]])),
        height: 140,
                padding: EdgeInsets.only(bottom: 30),
                  ),
        Positioned(
                top: 25,
                left: 25,
                child: Text(
                'Hi $user!',
                style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.bold,
                fontSize: 40),
                    ),
                  ),
        Positioned(
                bottom: 0,
                right: -18,
                child: Padding(
                padding: const EdgeInsets.fromLTRB(20, 0, 30, 0),
                child: Card(
                shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20)),
        elevation: 10,
                color: Colors.white,
                child: Row(
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
        SizedBox(
                width: MediaQuery.of(context).size.width - 150,
                child: Flexible(
                child: Padding(
                padding: const EdgeInsets.only(left: 15.0),
        child: TextField(
                controller: input,
                decoration: InputDecoration(
                hintText: 'Search for items here',
                enabledBorder: UnderlineInputBorder(
                borderSide: BorderSide(
                color: Colors.orangeAccent)),
        focusedBorder: UnderlineInputBorder(
                borderSide: BorderSide(
                color: Colors.orangeAccent))),
        style: Theme.of(context).textTheme.body1,
                                ),
                              )),
                            ),
        Padding(
                padding: const EdgeInsets.fromLTRB(15, 0, 15, 0),
                child: FlatButton(
                onPressed: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => search(
                    searchitems:
            Search(input.text))));
        },
        color: Colors.orangeAccent,
                textColor: Colors.white,
                child: Icon(
                Icons.search,
                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ]),
              ),
        Padding(
                padding: const EdgeInsets.only(top: 8.0),
        child: CarouselSlider(
                options: CarouselOptions(
                height: 200.0,
                autoPlay: true,
                autoPlayInterval: Duration(seconds: 3),
        autoPlayAnimationDuration: Duration(milliseconds: 800),
                  ),
        items: [
        'https://m.media-amazon.com/images/S/abs-image-upload-na/b/AmazonStores/A21TJRUUN4KGV/042d166f9592cb3827afdc05e311680a.w750.h750.png',
                'https://images-eu.ssl-images-amazon.com/images/G/31/img16/Grocery/SVD/Nov17/Bru_750x375.jpg',
                'https://www.pepsicoindia.co.in/images/librariesprovider16/default-album/real-flavours-of-life.png?sfvrsn=0',
                'https://showtoclients.com/presenations/BI/1486551767-Food-Beverage/Power-Point/Daawat-Presentation/slide-0.png',
                'https://content.jdmagicbox.com/quickquotes/images_main/milano_deliciously_rich_chocolate_chip_cookies_120_gm_52443940_1.jpg'
                  ].map((i) {
        return Builder(
                builder: (BuildContext context) {
            return Container(
                    width: MediaQuery.of(context).size.width,
                    margin: EdgeInsets.symmetric(horizontal: 5.0),
            decoration: BoxDecoration(color: Colors.amber),
            child: Image.network(
                    '$i',
                    fit: BoxFit.fill,
                            ));
        },
                    );
                  }).toList(),
                ),
              ),
        Padding(
                padding: const EdgeInsets.fromLTRB(22.0, 20, 0, 5),
                child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        RichText(
                text: TextSpan(
                text: 'Best of ',
                style: TextStyle(
                fontSize: 25,
                fontWeight: FontWeight.bold,
                color: Colors.black),
        children: <TextSpan>[
        TextSpan(
                text: 'Essentials',
                style: TextStyle(
                fontSize: 25,
                color: Colors.orangeAccent,
                fontWeight: FontWeight.bold)),
                            ]),
                      ),
                    ]),
              ),
        SizedBox(
                height: 204,
                child: ListView.builder(
                itemCount: litems.length,
                scrollDirection: Axis.horizontal,
                itemBuilder: (BuildContext ctxt, int index) {
            return Stack(children: <Widget>[
            Container(
                    width: 250,
                    padding: EdgeInsets.only(top: 50),
            child: GestureDetector(
                    onTap: () {
                Navigator.push(
                        context,
                        MaterialPageRoute(
                                builder: (context) => item(
                        itemn: litems[index],
                                          )));
            },
            child: Card(
                    elevation: 10,
                    child: Padding(
                    padding: const EdgeInsets.fromLTRB(0, 50, 0, 0),
                    child: Column(
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.fromLTRB(
                    15, 0, 15, 0),
                    child: Text(
                    litems[index].name,
                    style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 25,
                    color: Colors.black,
                                        ),
                                      ),
                                    ),
            Text(litems[index].price,
                    style: TextStyle(
                    color: Colors.grey,
                                        )),
            Padding(
                    padding: const EdgeInsets.only(top: 15.0),
            child: Row(children: <Widget>[
            FlatButton(
                    shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.only(
                    topRight:
            Radius.circular(20))),
            padding: EdgeInsets.fromLTRB(
                    22, 10, 22, 10),
                    materialTapTargetSize:
            MaterialTapTargetSize.shrinkWrap,
                    color: Colors.orangeAccent,
                    textColor: Colors.white,
                    disabledColor: Colors.orangeAccent,
                    disabledTextColor: Colors.white,
                    child: Text(
                    'Add to Cart',
                    style: TextStyle(
                    fontWeight: FontWeight.bold,
                                            ),
                                          ),
            onPressed: () {
                Navigator.push(
                        context,
                        MaterialPageRoute(
                                builder: (context) =>
                cart()));
            },
                                        ),
            FlatButton(
                    padding: EdgeInsets.fromLTRB(
                    15, 10, 15, 10),
                    materialTapTargetSize:
            MaterialTapTargetSize.shrinkWrap,
                    child: Text(
                    'Add to Wishlist',
                    style: TextStyle(
                    fontWeight: FontWeight.bold,
                                            ),
                                          ),
            onPressed: () {
                Navigator.push(
                        context,
                        MaterialPageRoute(
                                builder: (context) =>
                wishlist()));
            },
                                        ),
                                      ]),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ),
                        ),
            Positioned(
                    top: -1,
                    left: 70,
                    child: CircleAvatar(
                    radius: 50,
                    backgroundImage: NetworkImage(litems[index].imgurl),
                          ),
                        )
                      ]);
        }),
              ),
        Padding(
                padding: const EdgeInsets.fromLTRB(22.0, 20, 0, 5),
                child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        RichText(
                text: TextSpan(
                text: 'Shop By',
                style: TextStyle(
                fontSize: 25,
                fontWeight: FontWeight.bold,
                color: Colors.black),
        children: <TextSpan>[
        TextSpan(
                text: ' Category',
                style: TextStyle(
                fontSize: 25,
                color: Colors.orangeAccent,
                fontWeight: FontWeight.bold)),
                            ]),
                      ),
                    ]),
              ),
        Padding(
                padding: const EdgeInsets.all(15.0),
                child: GestureDetector(
                onTap: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => search(
                    searchitems: Search('Fruits and Vegetables'))));
        },
        child: Card(
                shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20)),
        color: Color.fromRGBO(155, 195, 63, 1),
                child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Container(
                child: Image.network(
                'https://pluspng.com/img-png/fruits-and-vegetables-png-hd-vegetable-png-transparent-image-1799.png',
                height: 100.0,
                width: 200.0,
                fit: BoxFit.fitHeight,
                        )),
        Container(
                child: Flexible(
                child: Padding(
                padding: const EdgeInsets.only(right: 25.0),
        child: Text(
                "Fruits and Vegetables",
                textAlign: TextAlign.right,
                style: TextStyle(
                color: Colors.white,
                fontSize: 28,
                fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),
                          ),
                        )
                      ],
                    ),
                  ),
                ),
              ),
        Padding(
                padding: const EdgeInsets.all(15.0),
                child: Stack(children: <Widget>[
        GestureDetector(
                onTap: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => search(
                    searchitems:
            Search('Staples and Household items'))));
        },
        child: Card(
                semanticContainer: true,
                clipBehavior: Clip.antiAliasWithSaveLayer,
                shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20)),
        color: Color.fromRGBO(155, 195, 63, 1),
                child: Image.network(
                'https://www.fontagro.org/wp-content/uploads/2019/07/concursos-web-1024x678.jpg',
                height: 150.0,
                width: MediaQuery.of(context).size.width,
                fit: BoxFit.fill,
                      ),
                    ),
                  ),
        Positioned(
                top: 25,
                child: GestureDetector(
                onTap: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => search(
                    searchitems: Search(
                    'Staples and Household items'))));
        },
        child: Container(
                width: MediaQuery.of(context).size.width / 2,
                child: Padding(
                padding: const EdgeInsets.fromLTRB(25, 0, 0, 0),
                child: Flexible(
                child: Text(
                "Staples and Household items",
                style: TextStyle(
                color: Colors.white,
                fontSize: 30,
                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ),
                        ),
                      ),
                    ),
                  )
                ]),
              ),
        Stack(
                children: <Widget>[
        Container(
                padding: EdgeInsets.only(top: 10),
        child: Padding(
                padding: const EdgeInsets.all(15.0),
                child: GestureDetector(
                onTap: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => search(
                    searchitems:
            Search('Beverages and Snacks'))));
        },
        child: Card(
                shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20)),
        color: Colors.red,
                child: Row(children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(20, 10, 0, 10),
                child: Container(
                width: MediaQuery.of(context).size.width / 2,
                child: Flexible(
                child: Text(
                'Beverages and Snacks',
                style: TextStyle(
                color: Colors.lime,
                fontWeight: FontWeight.bold,
                fontSize: 27,
                                    ),
                                  ),
                                ),
                              ),
                            ),
                          ]),
                        ),
                      ),
                    ),
                  ),
        Positioned(
                right: 0,
                child: GestureDetector(
                onTap: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => search(
                    searchitems:
            Search('Beverages and Snacks'))));
        },
        child: Image.network(
                'https://lazykart.in/addarea/uploads/product/snacks-transparent-beverage1553952063.png',
                height: 136,
                width: MediaQuery.of(context).size.width * 3 / 6,
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
    }
}

class Login extends StatefulWidget {
    @override
    _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
    String read = '';
    _LoginState() {
        _read().then((value) => setState(() {
            read = value;
        }));
    }
    @override
    Widget build(BuildContext context) {
        return Scaffold(
                backgroundColor: Colors.white,
                body: SafeArea(
                child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(0, 60, 0, 0),
                child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
        Text(
                'RetailName',
                textAlign: TextAlign.center,
                style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.orangeAccent,
                fontSize: 50,
                fontFamily: 'MeriendaOne'),
                  ),
                ],
              ),
            ),
        Padding(
                padding: const EdgeInsets.only(bottom: 50),
        child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(bottom: 20),
        child: Text(
                'Welcome!',
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 23,
                color: Colors.black),
                    ),
                  ),
        FlatButton(
                onPressed: () {
            if (read == '') {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => SignIn()));
            } else {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => MyApp()));
            }
        },
        padding: EdgeInsets.fromLTRB(
                MediaQuery.of(context).size.width / 4,
                0,
                MediaQuery.of(context).size.width / 4,
                0),
                color: Colors.orangeAccent,
                disabledColor: Colors.orangeAccent,
                child: Text(
                'Sign In',
                style: TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
        OutlineButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => register()));
        },
        padding: EdgeInsets.fromLTRB(
                MediaQuery.of(context).size.width / 4,
                0,
                MediaQuery.of(context).size.width / 4,
                0),
                disabledBorderColor: Colors.orangeAccent,
                child: Text(
                'Sign Up',
                style: TextStyle(color: Colors.orangeAccent),
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      ),
    );
    }
}

class SignIn extends StatefulWidget {
    @override
    _SignInState createState() => _SignInState();
}

class _SignInState extends State<SignIn> {
    final DatabaseReference database =
            FirebaseDatabase.instance.reference().child('users');
    final email = TextEditingController();
    final password = TextEditingController();
    var input = TextEditingController();
    List<Itemn> litems = [
    Itemn('Cauliflower', 'cauliflower bla bla', 'Rs.127',
                  'https://d2ebzu6go672f3.cloudfront.net/media/content/images/p7_Cauliflower_HH1810_gi887543438.jpg'),
    Itemn('Cauliflower', 'cauliflower bla bla', 'Rs.127',
                  'https://d2ebzu6go672f3.cloudfront.net/media/content/images/p7_Cauliflower_HH1810_gi887543438.jpg'),
    Itemn('Cauliflower', 'cauliflower bla bla', 'Rs.127',
                  'https://d2ebzu6go672f3.cloudfront.net/media/content/images/p7_Cauliflower_HH1810_gi887543438.jpg')
  ];
    String read = '';
    _SignInState() {
        _read().then((val) => setState(() {
            read = val;
        }));
    }

    @override
    Widget build(BuildContext context) {
        String user = read.split(';')[0].split(' ')[0];
        final key = new GlobalKey<ScaffoldState>();
        return Scaffold(
                resizeToAvoidBottomPadding: false,
                key: key,
                backgroundColor: Colors.white,
                floatingActionButton: FloatingActionButton(
                onPressed: () {
            if (email.text != '' && password.text != '') {
                database.once().then((DataSnapshot snap) {
                    var keys = snap.value.keys;
                    var data = snap.value;
                    bool account = false;
                    for (var key in keys) {
                        if (password.text == data[key]['password'] &&
                                email.text == data[key]['email']) {
                            account = true;
                            _write(data[key]['name'] +
                                    ';' +
                                    data[key]['email'] +
                                    ';' +
                                    data[key]['password'] +
                                    ';' +
                                    data[key]['phone'] +
                                    ';' +
                                    key.toString() +
                                    '; ');

                            Navigator.push(context,
                                    MaterialPageRoute(builder: (context) => MyApp()));
                        }
                    }
                    if (account == false) {
                        key.currentState.showSnackBar(SnackBar(
                                content: Text("Account does not exist"),
                ));
                    }
                });
            } else {
                key.currentState.showSnackBar(SnackBar(
                        content: Text("Please fill both the fields"),
            ));
            }
        },
        backgroundColor: Colors.orangeAccent,
                child: Icon(
                Icons.arrow_forward_ios,
                color: Colors.white,
        ),
      ),
        body: SafeArea(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 8.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        IconButton(
                icon: Icon(Icons.arrow_back_ios),
                onPressed: () {
            Navigator.pop(context);
        },
                  ),
                ],
              ),
            ),
        Padding(
                padding: const EdgeInsets.fromLTRB(15.0, 15, 0, 0),
                child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Text(
                'Welcome to RetailName',
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
                  ),
        Padding(
                padding: const EdgeInsets.only(left: 5.0),
        child: Text(
                "Let's get started",
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                color: Colors.grey),
                    ),
                  ),
        Form(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 35, 20, 20),
                child: TextFormField(
                controller: email,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'E-mail',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 0, 20, 0),
                child: TextFormField(
                controller: password,
                obscureText: true,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Password',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
    }
}

class register extends StatefulWidget {
    @override
    _registerState createState() => _registerState();
}

class _registerState extends State<register> {
    final DatabaseReference database =
            FirebaseDatabase.instance.reference().child('users');
    final name = TextEditingController();
    final email = TextEditingController();
    final phone = TextEditingController();
    final password = TextEditingController();
    final rpassword = TextEditingController();
    @override
    Widget build(BuildContext context) {
        final key = new GlobalKey<ScaffoldState>();
        return Scaffold(
                resizeToAvoidBottomPadding: false,
                key: key,
                backgroundColor: Colors.white,
                floatingActionButton: FloatingActionButton(
                onPressed: () {
            if (name.text.trim() != '' &&
                    phone.text.trim() != '' &&
                    email.text.trim() != '' &&
                    password.text.trim() != '' &&
                    rpassword.text.trim() != '') {
                if (password.text == rpassword.text) {
                    if (password.text.length >= 8) {
                        if (int.parse(phone.text) >= 1000000000 &&
                                int.parse(phone.text) <= 9999999999) {
                            if (email.text.contains('@') == true &&
                                    email.text.contains('.com') == true) {
                                database.push().set({
                                        'email': email.text,
                                        'name': name.text,
                                        'password': password.text,
                                        'phone': phone.text
                    });
                                Navigator.push(context,
                                        MaterialPageRoute(builder: (context) => SignIn()));
                            } else {
                                key.currentState.showSnackBar(SnackBar(
                                        content: Text("Invalid E-mail Id"),
                    ));
                            }
                        } else {
                            key.currentState.showSnackBar(SnackBar(
                                    content: Text("Invalid Phone no"),
                  ));
                        }
                    } else {
                        key.currentState.showSnackBar(SnackBar(
                                content: Text("Password is not strong enough"),
                ));
                    }
                } else {
                    key.currentState.showSnackBar(SnackBar(
                            content: Text("Passwords don't match"),
              ));
                }
            } else {
                key.currentState.showSnackBar(SnackBar(
                        content: Text("Some fields haven't been entered"),
            ));
            }
        },
        backgroundColor: Colors.orangeAccent,
                child: Icon(
                Icons.done,
                color: Colors.white,
        ),
      ),
        body: SafeArea(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 8.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        IconButton(
                icon: Icon(Icons.arrow_back_ios),
                onPressed: () {
            Navigator.pop(context);
        },
                  ),
                ],
              ),
            ),
        Padding(
                padding: const EdgeInsets.fromLTRB(15.0, 15, 0, 0),
                child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Text(
                'Welcome to RetailName',
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
                  ),
        Padding(
                padding: const EdgeInsets.only(left: 5.0),
        child: Text(
                "Let's get started",
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                color: Colors.grey),
                    ),
                  ),
        Form(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 35, 20, 15),
                child: TextFormField(
                controller: name,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Name',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 0, 20, 15),
                child: TextFormField(
                controller: phone,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Phone No',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 0, 20, 15),
                child: TextFormField(
                controller: email,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'E-mail',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 0, 20, 0),
                child: TextFormField(
                controller: password,
                obscureText: true,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Password',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 15, 20, 0),
                child: TextFormField(
                controller: rpassword,
                obscureText: true,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Confirm Password',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
    }
}

class account extends StatefulWidget {
    @override
    _accountState createState() => _accountState();
}

class _accountState extends State<account> {
    String read = '';
    _accountState() {
        _read().then((value) => setState(() {
            read = value;
        }));
    }
    @override
    Widget build(BuildContext context) {
        String name = read.split(';')[0];
        String email = read.split(';')[1];
        String phone = read.split(';')[3];
        return Scaffold(
                bottomNavigationBar: BottomAppBar(
                color: Colors.white,
                child: Padding(
                padding: const EdgeInsets.symmetric(vertical: 15.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
        IconButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => MyApp()));
        },
        icon: Icon(
                Icons.home,
                size: 40.0,
                  ),
                ),
        IconButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => wishlist()));
        },
        icon: Icon(
                Icons.favorite,
                size: 40.0,
                  ),
                ),
        IconButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => cart()));
        },
        icon: Icon(
                Icons.shopping_cart,
                size: 40.0,
                  ),
                ),
        IconButton(
                icon: Icon(
                Icons.account_circle,
                color: Colors.orangeAccent,
                size: 40.0,
                  ),
                ),
              ],
            ),
          ),
        ),
        body: SafeArea(
                child: Column(
                children: <Widget>[
        Container(
                decoration: BoxDecoration(
                color: Colors.white,
                borderRadius:
        BorderRadius.vertical(bottom: Radius.circular(20)),
        boxShadow: [
        BoxShadow(
                color: Colors.grey,
                blurRadius: 3.0,
                spreadRadius: 0.0,
                offset: Offset(2.0, 2.0),
                      )
                    ]),
        width: MediaQuery.of(context).size.width,
                child: Column(
                children: <Widget>[
        Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: <Widget>[
        IconButton(
                onPressed: () {
            Navigator.push(
                    context,
                    MaterialPageRoute(
                            builder: (context) => edit()));
        },
        icon: Icon(Icons.mode_edit),
                        )
                      ],
                    ),
        Padding(
                padding: const EdgeInsets.only(top: 15.0),
        child: CircleAvatar(
                radius: 70,
                backgroundColor: Colors.white,
                backgroundImage: NetworkImage(
                'https://static.thenounproject.com/png/363633-200.png'),
                      ),
                    ),
        Padding(
                padding: const EdgeInsets.symmetric(vertical: 10.0),
        child: Text(
                "$name",
                style: TextStyle(
                color: Colors.black,
                fontWeight: FontWeight.bold,
                fontSize: 30),
                      ),
                    ),
        Padding(
                padding: const EdgeInsets.only(bottom: 5.0),
        child: Text(
                "$email",
                style: TextStyle(fontSize: 20, color: Colors.grey[700]),
                      ),
                    ),
        Padding(
                padding: const EdgeInsets.only(bottom: 50.0),
        child: Text(
                "$phone",
                style: TextStyle(fontSize: 20, color: Colors.grey),
                      ),
                    ),
                  ],
                ),
              ),
        Padding(
                padding: const EdgeInsets.only(top: 25.0),
        child: FlatButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => pass()));
        },
        child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Row(
                children: <Widget>[
        Icon(
                Icons.lock_outline,
                color: Colors.grey,
                          ),
        SizedBox(
                width: 10,
                          ),
        Text(
                "Change Password",
                style: TextStyle(fontWeight: FontWeight.bold),
                          )
                        ],
                      ),
        Icon(
                Icons.arrow_forward_ios,
                size: 20,
                color: Colors.grey,
                      )
                    ],
                  ),
                ),
              ),
        FlatButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => address()));
        },
        child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Row(
                children: <Widget>[
        Icon(
                Icons.add_location,
                color: Colors.grey,
                        ),
        SizedBox(
                width: 10,
                        ),
        Text(
                "Delivery address",
                style: TextStyle(fontWeight: FontWeight.bold),
                        )
                      ],
                    ),
        Icon(
                Icons.arrow_forward_ios,
                size: 20,
                color: Colors.grey,
                    )
                  ],
                ),
              ),
        FlatButton(
                onPressed: () {
            _write('');
            Navigator.push(context,
                    MaterialPageRoute(builder: (context) => Login()));
        },
        child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Row(
                children: <Widget>[
        Icon(
                Icons.exit_to_app,
                color: Colors.grey,
                        ),
        SizedBox(
                width: 10,
                        ),
        Text(
                "Logout",
                style: TextStyle(fontWeight: FontWeight.bold),
                        )
                      ],
                    ),
                  ],
                ),
              ),
            ],
          ),
        ));
    }
}

class pass extends StatefulWidget {
    @override
    _passState createState() => _passState();
}

class _passState extends State<pass> {
    TextEditingController password = TextEditingController();
    TextEditingController rpassword = TextEditingController();
    final DatabaseReference firebase = FirebaseDatabase.instance.reference();
    String read = '';
    _passState() {
        _read().then((value) => setState(() {
            read = value;
        }));
    }
    @override
    Widget build(BuildContext context) {
        final key = new GlobalKey<ScaffoldState>();
        String firekey = read.split(';')[4];
        return Scaffold(
                resizeToAvoidBottomPadding: false,
                key: key,
                backgroundColor: Colors.white,
                floatingActionButton: FloatingActionButton(
                onPressed: () {
            if (password.text == rpassword.text) {
                if (password.text.length >= 8) {
                    firebase.child('users/$firekey').set({
                            'password': password.text,
                            'name': read.split(';')[0],
                            'email': read.split(';')[1],
                            'phone': read.split(';')[3]
              });
                    _write(read.split(read.split(';')[2])[0] +
                            password.text +
                            read.split(read.split(';')[2])[1]);
                    Navigator.push(
                            context, MaterialPageRoute(builder: (context) => account()));
                } else {
                    key.currentState.showSnackBar(SnackBar(
                            content: Text('Password not strong enough'),
              ));
                }
            } else {
                key.currentState.showSnackBar(
                        SnackBar(content: Text('Passwords do not match')));
            }
        },
        backgroundColor: Colors.orangeAccent,
                child: Icon(
                Icons.done,
                color: Colors.white,
        ),
      ),
        body: SafeArea(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 8.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        IconButton(
                icon: Icon(Icons.arrow_back_ios),
                onPressed: () {
            Navigator.pop(context);
        },
                  ),
                ],
              ),
            ),
        Padding(
                padding: const EdgeInsets.fromLTRB(15.0, 15, 0, 0),
                child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Text(
                'Change Password',
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
                  ),
        Padding(
                padding: const EdgeInsets.only(left: 5.0),
        child: Text(
                "Make sure it is strong and alphanumeric ",
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                color: Colors.grey),
                    ),
                  ),
        Form(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 35, 20, 20),
                child: TextFormField(
                controller: password,
                obscureText: true,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Password',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 0, 20, 0),
                child: TextFormField(
                controller: rpassword,
                obscureText: true,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Confirm Password',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
    }
}

class edit extends StatefulWidget {
    @override
    _editState createState() => _editState();
}

class _editState extends State<edit> {
    TextEditingController name = TextEditingController();
    TextEditingController email = TextEditingController();
    TextEditingController phone = TextEditingController();
    final DatabaseReference firebase = FirebaseDatabase.instance.reference();
    String read = '';
    _editState() {
        _read().then((value) => setState(() {
            read = value;
        }));
    }
    @override
    Widget build(BuildContext context) {
        final key = new GlobalKey<ScaffoldState>();
        String firekey = read.split(';')[4];
        return Scaffold(
                resizeToAvoidBottomPadding: false,
                key: key,
                backgroundColor: Colors.white,
                floatingActionButton: FloatingActionButton(
                onPressed: () {
            if (name.text != '' && email.text != '' && phone.text != '') {
                if (int.parse(phone.text) >= 1000000000 &&
                        int.parse(phone.text) <= 9999999999) {
                    if (email.text.contains('@') == true &&
                            email.text.contains('.com') == true) {
                        firebase.child('users/$firekey').set({
                                'password': read.split(';')[2],
                                'name': name.text,
                                'email': email.text,
                                'phone': phone.text
                });
                        _write(name.text +
                                ';' +
                                email.text +
                                ';' +
                                read.split(';')[2] +
                                ';' +
                                phone.text +
                                ';' +
                                firekey +
                                '; ');
                        Navigator.push(context,
                                MaterialPageRoute(builder: (context) => account()));
                    } else {
                        key.currentState
                                .showSnackBar(SnackBar(content: Text('Invalid E-mail Id')));
                    }
                } else {
                    key.currentState
                            .showSnackBar(SnackBar(content: Text('Invalid Phone no')));
                }
            } else {
                key.currentState.showSnackBar(
                        SnackBar(content: Text('Please fill all the fields')));
            }
        },
        backgroundColor: Colors.orangeAccent,
                child: Stack(
                children: <Widget>[
        Icon(
                Icons.done,
                color: Colors.white,
            ),
          ],
        ),
      ),
        body: SafeArea(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 8.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        IconButton(
                icon: Icon(Icons.arrow_back_ios),
                onPressed: () {
            Navigator.pop(context);
        },
                  ),
                ],
              ),
            ),
        Padding(
                padding: const EdgeInsets.fromLTRB(15.0, 15, 0, 0),
                child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Text(
                'Edit Profile',
                style: TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
                  ),
        Padding(
                padding: const EdgeInsets.only(left: 5.0),
        child: Text(
                "Enter all the fields",
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                color: Colors.grey),
                    ),
                  ),
        Form(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 35, 20, 20),
                child: TextFormField(
                controller: name,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Name',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 0, 20, 0),
                child: TextFormField(
                controller: email,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'E-mail',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 20, 20, 0),
                child: TextFormField(
                controller: phone,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Phone',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border:
        OutlineInputBorder(borderSide: BorderSide()),
                            ),
                          ),
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
    }
}

class address extends StatefulWidget {
    @override
    _addressState createState() => _addressState();
}

class _addressState extends State<address> {
    String read = '';
    TextEditingController delivery1 = TextEditingController();
    _addressState() {
        _read().then((value) => setState(() {
            read = value;
        }));
    }

    @override
    Widget build(BuildContext context) {
        String delivery = read.split(';')[5];
        return Scaffold(
                resizeToAvoidBottomPadding: false,
                backgroundColor: Colors.white,
                floatingActionButton: FloatingActionButton(
                onPressed: () {
            _write(read.split(';')[0] +
                    ';' +
                    read.split(';')[1] +
                    ';' +
                    read.split(';')[2] +
                    ';' +
                    read.split(';')[3] +
                    ';' +
                    read.split(';')[4] +
                    ';' +
                    delivery1.text);
            Navigator.push(
                    context, MaterialPageRoute(builder: (context) => account()));
        },
        backgroundColor: Colors.orangeAccent,
                child: Icon(
                Icons.done,
                color: Colors.white,
        ),
      ),
        body: SafeArea(
                child: SingleChildScrollView(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 8.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
        IconButton(
                icon: Icon(Icons.arrow_back_ios),
                onPressed: () {
            Navigator.pop(context);
        },
                    ),
                  ],
                ),
              ),
        Padding(
                padding: const EdgeInsets.fromLTRB(15.0, 15, 0, 0),
                child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Text(
                'Delivery Address',
                style:
        TextStyle(fontWeight: FontWeight.bold, fontSize: 30),
                    ),
        Padding(
                padding: const EdgeInsets.only(left: 5.0),
        child: Text(
                (delivery == ' ' || delivery == '')
                        ? "No address has been entered"
                        : delivery,
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                color: Colors.grey),
                      ),
                    ),
        Form(
                child: Column(
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.fromLTRB(5, 35, 20, 20),
                child: TextFormField(
                controller: delivery1,
                maxLines: 5,
                decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
        hintText: 'Address',
                focusedBorder: OutlineInputBorder(
                borderSide:
        BorderSide(color: Colors.orangeAccent)),
        border: OutlineInputBorder(
                borderSide: BorderSide()),
                              ),
                            ),
                          ),
                        ],
                      ),
                    )
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
    }
}

class item extends StatefulWidget {
    final Itemn itemn;
    item({Key key, @required this.itemn}) : super(key: key);
    @override
    _itemState createState() => _itemState();
}

class _itemState extends State<item> {
    int quantity = 1;
    String read = '';
    String cartread = '';
    _itemState() {
        _wishlist_read().then((value) => setState(() {
            read = value;
        }));
        _cart_read().then((value) => setState(() {
            cartread = value;
        }));
    }
    @override
    Widget build(BuildContext context) {
        final key = new GlobalKey<ScaffoldState>();
        return Scaffold(
                key: key,
                appBar: AppBar(
                backgroundColor: Colors.orangeAccent,
                leading: IconButton(
                icon: Icon(Icons.arrow_back, color: Colors.white),
        onPressed: () => Navigator.of(context).pop(),
        ),
        centerTitle: true,
      ),
        bottomNavigationBar: BottomAppBar(
                child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Expanded(
                child: FlatButton(
                onPressed: () {
            var duplicate = false;
            for (var k = 0; k < cartread.split('|').length; k++) {
                if (cartread.split('|')[k].split(';')[0] ==
                        widget.itemn.name) {
                    key.currentState.showSnackBar(SnackBar(
                            content: Text('Added to cart'),
                            action: SnackBarAction(
                            label: 'Go to Cart',
                            onPressed: () {
                        Navigator.push(
                                context,
                                MaterialPageRoute(
                                        builder: (content) => cart()));
                    }),
                      ));
                    duplicate = true;
                    int newquant =
                            int.parse(cartread.split('|')[k].split(';')[4]) +
                                    quantity;
                    _cart_write(cartread.replaceAll(
                            widget.itemn.name +
                                    ';' +
                                    widget.itemn.imgurl +
                                    ';' +
                                    widget.itemn.price +
                                    ';' +
                                    widget.itemn.description +
                                    ';' +
                                    cartread.split('|')[k].split(';')[4] +
                                    '|',
                            widget.itemn.name +
                                    ';' +
                                    widget.itemn.imgurl +
                                    ';' +
                                    widget.itemn.price +
                                    ';' +
                                    widget.itemn.description +
                                    ';' +
                                    newquant.toString() +
                                    '|'));
                }
            }
            if (duplicate == false) {
                _cart_write(cartread +
                        widget.itemn.name +
                        ';' +
                        widget.itemn.imgurl +
                        ';' +
                        widget.itemn.price +
                        ';' +
                        widget.itemn.description +
                        ';' +
                        quantity.toString() +
                        '|');
                key.currentState.showSnackBar(SnackBar(
                        content: Text('Added to cart'),
                        action: SnackBarAction(
                        label: 'Go to Cart',
                        onPressed: () {
                    Navigator.push(
                            context,
                            MaterialPageRoute(
                                    builder: (content) => cart()));
                })));
            }
        },
        padding: EdgeInsets.symmetric(vertical: 15),
        shape: RoundedRectangleBorder(
                borderRadius:
        BorderRadius.only(topRight: Radius.circular(20))),
        color: Colors.orangeAccent,
                textColor: Colors.white,
                child: Text(
                'Add to cart',
                style: TextStyle(fontWeight: FontWeight.bold),
                ),
              ),
            ),
        Expanded(
                child: FlatButton(
                onPressed: () {
            bool duplicate = false;
            for (var k = 0; k < read.split('|').length; k++) {
                if (read.split('|')[k].split(';')[0] == widget.itemn.name) {
                    key.currentState.showSnackBar(SnackBar(
                            content: Text('Already added to wishlist'),
                            action: SnackBarAction(
                            label: 'Go to Wishlist',
                            onPressed: () {
                        Navigator.push(
                                context,
                                MaterialPageRoute(
                                        builder: (content) => wishlist()));
                    })));
                    duplicate = true;
                }
            }
            if (duplicate == false) {
                _wishlist_write(read +
                        widget.itemn.name +
                        ';' +
                        widget.itemn.imgurl +
                        ';' +
                        widget.itemn.price +
                        ';' +
                        widget.itemn.description +
                        '|');
                key.currentState.showSnackBar(SnackBar(
                        content: Text('Added to wishlist'),
                        action: SnackBarAction(
                        label: 'Go to Wishlist',
                        onPressed: () {
                    Navigator.push(
                            context,
                            MaterialPageRoute(
                                    builder: (content) => wishlist()));
                })));
            }
        },
        padding: EdgeInsets.symmetric(vertical: 15),
        child: Text(
                'Add to wishlist',
                style: TextStyle(
                fontWeight: FontWeight.bold, color: Colors.grey),
                ),
              ),
            )
          ],
        ),
      ),
        body: SafeArea(
                child: Stack(
                children: <Widget>[
        Container(height: MediaQuery.of(context).size.height),
        SingleChildScrollView(
                scrollDirection: Axis.vertical,
                child: Column(
                mainAxisSize: MainAxisSize.max,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Column(
                children: <Widget>[
        Image.network(
                widget.itemn.imgurl,
                ),
                Padding(
                        padding: const EdgeInsets.fromLTRB(15, 15.0, 15, 5),
                child: Flexible(
                child: Text(
                widget.itemn.name,
                style: TextStyle(
                fontWeight: FontWeight.bold,
                fontFamily: 'MeriendaOne',
                fontSize: 20,
                            ),
                          ),
                        ),
                      ),
        Text(
                widget.itemn.price,
                style: TextStyle(
                fontWeight: FontWeight.w900,
                fontSize: 30,
                fontFamily: 'MeriendaOne'),
                      ),
        Padding(
                padding: const EdgeInsets.only(top: 20.0),
        child: Text(
                'Quantity',
                style: TextStyle(color: Colors.black),
                        ),
                      ),
        Padding(
                padding: const EdgeInsets.only(top: 15.0),
        child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
        ButtonTheme(
                materialTapTargetSize:
        MaterialTapTargetSize.shrinkWrap,
                minWidth: 0,
                height: 0,
                child: OutlineButton(
                borderSide: BorderSide(
                color: Colors.orangeAccent,
                                ),
        shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(100)),
        color: Colors.orangeAccent,
                onPressed: () {
            if (quantity > 1) {
                setState(() {
                    quantity = quantity - 1;
                });
            }
        },
        child: Text(
                '-',
                style: TextStyle(
                color: Colors.orangeAccent, fontSize: 40),
                                ),
                              ),
                            ),
        Padding(
                padding:
                                  const EdgeInsets.symmetric(horizontal: 15.0),
        child: Text(
                '$quantity',
                style: TextStyle(
                fontWeight: FontWeight.bold, fontSize: 40),
                              ),
                            ),
        ButtonTheme(
                materialTapTargetSize:
        MaterialTapTargetSize.shrinkWrap,
                minWidth: 0,
                height: 0,
                child: OutlineButton(
                padding: EdgeInsets.fromLTRB(13, 1, 13, 1),
                borderSide:
        BorderSide(color: Colors.orangeAccent),
        shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(100)),
        color: Colors.orangeAccent,
                onPressed: () {
            setState(() {
                quantity = quantity + 1;
            });
        },
        child: Text(
                '+',
                style: TextStyle(
                color: Colors.orangeAccent, fontSize: 40),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
        Padding(
                padding: const EdgeInsets.symmetric(horizontal: 15.0),
        child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 25.0),
        child: Text(
                'Description',
                textAlign: TextAlign.left,
                style: TextStyle(
                color: Colors.grey[600],
                fontSize: 25,
                fontFamily: 'MeriendaOne',
                fontWeight: FontWeight.bold),
                          ),
                        ),
        Padding(
                padding: const EdgeInsets.fromLTRB(3, 8.0, 0, 0),
                child: Flexible(
                child: Text(
                widget.itemn.description,
                textAlign: TextAlign.left,
                style: TextStyle(color: Colors.grey),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
        SizedBox(
                height: 15,
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
    }
}

class cart extends StatefulWidget {
    @override
    _cartState createState() => _cartState();
}

class _cartState extends State<cart> {
    final DatabaseReference database =
            FirebaseDatabase.instance.reference().child('orders').push();
    String read = '';
    String accountread = '';
    int price = 0;
    int _value = 1;
    int totalquantity = 0;
    _cartState() {
        _read().then((value) => setState(() {
            accountread = value;
        }));
        _cart_read().then((value) => setState(() {
            read = value;

            for (var l = 0; l < read.split('|').length - 1; l++) {
                price = price +
                        int.parse(read.split('|')[l].split(';')[2].substring(3)) *
                                int.parse(read.split('|')[l].split(';')[4]);
                print(price);
            }
            for (var l = 0; l < read.split('|').length - 1; l++) {
                totalquantity =
                        totalquantity + int.parse(read.split('|')[l].split(';')[4]);
                print(totalquantity);
            }
        }));
    }
    @override
    Widget build(BuildContext context) {
        final key = GlobalKey<ScaffoldState>();
        if (read == '') {
            return Scaffold(
                    bottomNavigationBar: BottomAppBar(
                    color: Colors.white,
                    child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 15.0),
            child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => MyApp()));
            },
            icon: Icon(
                    Icons.home,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => wishlist()));
            },
            icon: Icon(
                    Icons.favorite,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (content) => cart()));
            },
            icon: Icon(
                    Icons.shopping_cart,
                    size: 40.0,
                    color: Colors.orangeAccent,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => account()));
            },
            icon: Icon(
                    Icons.account_circle,
                    size: 40.0,
                  ),
                ),
              ],
            ),
          ),
        ),
            body: SafeArea(
                    child: Container(
                    color: Colors.white,
                    child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.only(top: 30),
            child: Image.asset('images/cart.jpg'),
              ),
            Column(
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.only(bottom: 10.0),
            child: Text(
                    'Your cart is empty',
                    style: TextStyle(
                    color: Colors.black,
                    fontSize: 25,
                    fontWeight: FontWeight.bold),
                    ),
                  ),
            Padding(
                    padding: const EdgeInsets.only(bottom: 25.0),
            child: Text(
                    'You haven\'t added any items to your cart yet',
                    style: TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.grey[400],
                    fontSize: 15),
                    ),
                  ),
            Padding(
                    padding: const EdgeInsets.only(bottom: 80.0),
            child: FlatButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (content) => MyApp()));
            },
            color: Colors.orangeAccent,
                    textColor: Colors.white,
                    child: Text(
                    "Continue Shopping",
                    style:
            TextStyle(fontSize: 20, fontFamily: 'MeriendaOne'),
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        )),
      );
        } else {
            return Scaffold(
                    key: key,
                    bottomNavigationBar: BottomAppBar(
                    color: Colors.white,
                    child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 15.0),
            child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => MyApp()));
            },
            icon: Icon(
                    Icons.home,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => wishlist()));
            },
            icon: Icon(
                    Icons.favorite,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (content) => cart()));
            },
            icon: Icon(
                    Icons.shopping_cart,
                    size: 40.0,
                    color: Colors.orangeAccent,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => account()));
            },
            icon: Icon(
                    Icons.account_circle,
                    size: 40.0,
                  ),
                ),
              ],
            ),
          ),
        ),
            body: SafeArea(
                    child: Stack(
                    children: <Widget>[
            Container(height: MediaQuery.of(context).size.height),
            Column(
                    mainAxisSize: MainAxisSize.max,
                    children: <Widget>[
            Expanded(
                    child: ListView.builder(
                    itemCount: read.split('|').length - 1,
                    itemBuilder: (BuildContext cxt, int index) {
                return Column(
                        children: <Widget>[
                Row(
                        children: <Widget>[
                Expanded(
                        flex: 1,
                        child: Image.network(
                        read.split('|')[index].split(';')[1],
                        fit: BoxFit.fitHeight,
                                      )),
                Expanded(
                        flex: 2,
                        child: Padding(
                        padding:
                                          const EdgeInsets.only(left: 10.0),
                child: Column(
                        mainAxisAlignment:
                MainAxisAlignment.start,
                        crossAxisAlignment:
                CrossAxisAlignment.start,
                        children: <Widget>[
                Padding(
                        padding: const EdgeInsets.only(
                        top: 10.0),
                child: Text(
                        read
                                .split('|')[index]
                                .split(';')[0],
                        style: TextStyle(
                        fontWeight: FontWeight.bold),
                                            ),
                                          ),
                RichText(
                        text: TextSpan(
                        text: 'Unit Price:',
                        style: TextStyle(
                        color: Colors.black),
                children: <TextSpan>[
                TextSpan(
                        text: read
                        .split('|')[index]
                        .split(';')[2],
                        style: TextStyle(
                        color: Colors.black,
                        fontWeight:
                FontWeight.bold)),
                                                ]),
                                          ),
                Padding(
                        padding: const EdgeInsets.only(
                        bottom: 5.0),
                child: Row(
                        mainAxisAlignment:
                MainAxisAlignment
                        .spaceBetween,
                        children: <Widget>[
                Row(
                        mainAxisAlignment:
                MainAxisAlignment.center,
                        children: <Widget>[
                ButtonTheme(
                        materialTapTargetSize:
                MaterialTapTargetSize
                        .shrinkWrap,
                        minWidth: 0,
                        height: 0,
                        child: OutlineButton(
                        padding:
                EdgeInsets.fromLTRB(
                        13, 4, 13, 5),
                        borderSide: BorderSide(
                        color: Colors
                        .orangeAccent),
                shape: RoundedRectangleBorder(
                        borderRadius:
                BorderRadius
                        .circular(
                                100)),
                color:
                Colors.orangeAccent,
                        onPressed: () {
                    int add = int.parse(read
                            .split('|')[
                            index]
                            .split(
                                    ';')[4]) -
                            1;
                    String from =
                            read.split('|')[
                                    index] +
                                    '|';
                    String to = read
                            .split('|')[
                            index]
                            .split(
                                    ';')[0] +
                            ';' +
                            read
                                    .split('|')[
                                    index]
                                    .split(
                                            ';')[1] +
                            ';' +
                            read
                                    .split('|')[
                                    index]
                                    .split(
                                            ';')[2] +
                            ';' +
                            read
                                    .split('|')[
                                    index]
                                    .split(
                                            ';')[3] +
                            ';' +
                            add.toString() +
                            '|';
                    if (add > 0) {
                        _cart_write(read
                                .replaceFirst(
                                        from, to));
                        setState(() {
                            price = 0;
                            totalquantity = 0;
                            read = read
                                    .replaceFirst(
                                            from, to);
                            print(read);
                            for (var l = 0;
                                 l <
                                         read.split('|').length -
                                                 1;
                                 l++) {
                                price = price +
                                        int.parse(read
                                                .split('|')[
                                                l]
                                                .split(';')[
                                                2]
                                                .substring(
                                                        3)) *
                                                int.parse(read
                                                        .split('|')[l]
                                                        .split(';')[4]);
                                print(price);
                            }
                            for (var l = 0;
                                 l <
                                         read.split('|').length -
                                                 1;
                                 l++) {
                                totalquantity = totalquantity +
                                        int.parse(read
                                                .split('|')[
                                                l]
                                                .split(
                                                        ';')[4]);
                                print(
                                        totalquantity);
                            }
                        });
                    }
                },
                child: Text(
                        '-',
                        style: TextStyle(
                        color: Colors
                        .orangeAccent,
                        fontSize: 20),
                                                        ),
                                                      ),
                                                    ),
                Padding(
                        padding: const EdgeInsets
                        .symmetric(
                                horizontal: 15.0),
                child: Text(
                        read
                                .split('|')[index]
                                .split(';')[4],
                        style: TextStyle(
                        fontWeight:
                FontWeight.bold,
                        fontSize: 20),
                                                      ),
                                                    ),
                ButtonTheme(
                        materialTapTargetSize:
                MaterialTapTargetSize
                        .shrinkWrap,
                        minWidth: 0,
                        height: 0,
                        child: OutlineButton(
                        padding:
                EdgeInsets.fromLTRB(
                        10, 4, 10, 5),
                        borderSide: BorderSide(
                        color: Colors
                        .orangeAccent,
                                                        ),
                shape: RoundedRectangleBorder(
                        borderRadius:
                BorderRadius
                        .circular(
                                100)),
                color:
                Colors.orangeAccent,
                        onPressed: () {
                    int add = int.parse(read
                            .split('|')[
                            index]
                            .split(
                                    ';')[4]) +
                            1;
                    String from =
                            read.split('|')[
                                    index] +
                                    '|';
                    String to = read
                            .split('|')[
                            index]
                            .split(
                                    ';')[0] +
                            ';' +
                            read
                                    .split('|')[
                                    index]
                                    .split(
                                            ';')[1] +
                            ';' +
                            read
                                    .split('|')[
                                    index]
                                    .split(
                                            ';')[2] +
                            ';' +
                            read
                                    .split('|')[
                                    index]
                                    .split(
                                            ';')[3] +
                            ';' +
                            add.toString() +
                            '|';
                    _cart_write(
                            read.replaceFirst(
                                    from, to));
                    setState(() {
                        price = 0;
                        totalquantity = 0;
                        read = read
                                .replaceFirst(
                                        from, to);
                        print(read);
                        for (var l = 0;
                             l <
                                     read.split('|').length -
                                             1;
                             l++) {
                            price = price +
                                    int.parse(read
                                            .split('|')[
                                            l]
                                            .split(';')[
                                            2]
                                            .substring(
                                                    3)) *
                                            int.parse(read
                                                    .split('|')[
                                                    l]
                                                    .split(
                                                            ';')[4]);
                            print(price);
                        }
                        for (var l = 0;
                             l <
                                     read.split('|').length -
                                             1;
                             l++) {
                            totalquantity = totalquantity +
                                    int.parse(read
                                            .split('|')[
                                            l]
                                            .split(
                                                    ';')[4]);
                            print(
                                    totalquantity);
                        }
                    });
                },
                child: Text(
                        '+',
                        style: TextStyle(
                        color: Colors
                        .orangeAccent,
                        fontSize: 20),
                                                        ),
                                                      ),
                                                    ),
                                                  ],
                                                ),
                Padding(
                        padding:
                                                      const EdgeInsets.only(
                        right: 15.0),
                child: IconButton(
                        onPressed: () {
                    _cart_write(
                            read.replaceFirst(
                                    read.split('|')[
                                            index] +
                                            '|',
                                    ''));
                    setState(() {
                        price = 0;
                        totalquantity = 0;
                        read =
                                read.replaceFirst(
                                        read.split('|')[
                                                index] +
                                                '|',
                                        '');
                        for (var l = 0;
                             l <
                                     read
                                             .split(
                                                     '|')
                                             .length -
                                             1;
                             l++) {
                            price = price +
                                    int.parse(read
                                            .split('|')[
                                            l]
                                            .split(';')[
                                            2]
                                            .substring(
                                                    3)) *
                                            int.parse(read
                                                    .split('|')[
                                                    l]
                                                    .split(
                                                            ';')[4]);
                            print(price);
                        }
                        for (var l = 0;
                             l <
                                     read
                                             .split(
                                                     '|')
                                             .length -
                                             1;
                             l++) {
                            totalquantity =
                                    totalquantity +
                                            int.parse(read
                                                    .split('|')[
                                                    l]
                                                    .split(
                                                            ';')[4]);
                            print(totalquantity);
                        }
                    });
                },
                icon: Icon(Icons.delete,
                        color: Colors.red),
                                                  ),
                                                )
                                              ],
                                            ),
                                          )
                                        ],
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                Container(
                        height: 1,
                        width: MediaQuery.of(context).size.width,
                        color: Colors.grey.shade300,
                              ),
                            ],
                          );
            }),
                  ),
            SizedBox(
                    height: 60,
                  ),
                ],
              ),
            Positioned(
                    bottom: 0,
                    child: GestureDetector(
                    onTap: () {
                if (accountread.split(';')[5].trim() == '') {
                    key.currentState.showSnackBar(SnackBar(
                            action: SnackBarAction(
                            label: "Go to address",
                            onPressed: () {
                        Navigator.push(
                                context,
                                MaterialPageRoute(
                                        builder: (context) => address()));
                    }),
                    content: Text(
                            "Please enter your delivery address before proceeding"),
                      ));
                } else {
                    showModalBottomSheet(
                            context: context,
                            builder: (BuildContext context) {
                        return Container(
                                color: Colors.grey[600],
                                height: 310,
                                child: Container(
                                decoration: BoxDecoration(
                                color: Colors.white,
                                borderRadius: BorderRadius.vertical(
                                top: Radius.circular(20))),
                        child: Column(
                                children: <Widget>[
                        Padding(
                                padding: const EdgeInsets.fromLTRB(
                                15.0, 15, 15, 0),
                                child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.spaceBetween,
                                children: <Widget>[
                        Text(
                                'Items:',
                                style:
                        TextStyle(color: Colors.grey),
                                          ),
                        Text(
                                "Total Price:",
                                style:
                        TextStyle(color: Colors.grey),
                                          )
                                        ],
                                      ),
                                    ),
                        Padding(
                                padding: const EdgeInsets.all(10.0),
                                child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.spaceBetween,
                                children: <Widget>[
                        Text(
                                "$totalquantity Items",
                                style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 20),
                                          ),
                        Text(
                                'Rs.$price',
                                style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 20),
                                          )
                                        ],
                                      ),
                                    ),
                        Padding(
                                padding:
                                          const EdgeInsets.only(bottom: 8.0),
                        child: Container(
                                color: Colors.grey[300],
                                height: 1,
                                width:
                        MediaQuery.of(context).size.width,
                                      ),
                                    ),
                        InkWell(
                                onTap: () {
                            Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                            builder: (context) =>
                            account()));
                        },
                        child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.start,
                                children: <Widget>[
                        Padding(
                                padding: EdgeInsets.fromLTRB(
                                15.0,
                                0,
                                MediaQuery.of(context)
                                        .size
                                        .width /
                                        3,
                                15),
                                child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.start,
                                children: <Widget>[
                        Text(
                                "Deliver to:",
                                style: TextStyle(
                                color: Colors.grey),
                                                ),
                                              ],
                                            ),
                                          ),
                        Expanded(
                                child: Column(
                                crossAxisAlignment:
                        CrossAxisAlignment.start,
                                children: <Widget>[
                        Padding(
                                padding: const EdgeInsets
                                .symmetric(
                                        horizontal: 10.0),
                        child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.start,
                                children: <Widget>[
                        Expanded(
                                child: Text(
                                accountread
                                        .split(';')[0],
                                style: TextStyle(
                                fontWeight:
                        FontWeight
                                .bold,
                                fontSize: 15),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                        Padding(
                                padding:
                                                      const EdgeInsets.fromLTRB(
                                15.0, 8, 15, 0),
                                child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.start,
                                children: <Widget>[
                        Expanded(
                                child: Text(accountread
                                .split(
                                        ';')[5]
                                .length <=
                                67
                                ? accountread
                                .split(';')[5]
                                : accountread
                                .split(
                                        ';')[5]
                                .substring(
                                        0, 65) +
                                '...'),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ],
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                        Padding(
                                padding: const EdgeInsets.symmetric(
                                vertical: 8.0),
                        child: Container(
                                color: Colors.grey[300],
                                height: 1,
                                width:
                        MediaQuery.of(context).size.width,
                                      ),
                                    ),
                        Row(
                                mainAxisAlignment:
                        MainAxisAlignment.start,
                                children: <Widget>[
                        Padding(
                                padding: EdgeInsets.fromLTRB(
                                15.0,
                                0,
                                MediaQuery.of(context)
                                        .size
                                        .width /
                                        3,
                                15),
                                child: Row(
                                mainAxisAlignment:
                        MainAxisAlignment.start,
                                children: <Widget>[
                        Text(
                                "Pay with:",
                                style: TextStyle(
                                color: Colors.grey),
                                              ),
                                            ],
                                          ),
                                        ),
                        Expanded(
                                child: Column(
                                children: <Widget>[
                        DropdownButton(
                                value: _value,
                                items: [
                        DropdownMenuItem(
                                child: Text(
                                "Payment On Delivery"),
                                value: 1,
                                                    ),
                        DropdownMenuItem(
                                child: Text(
                                "Credit Card/Debit Cart"),
                                value: 2,
                                                    ),
                                                  ],
                        onChanged: (value) {
                                setState(() {
                                _value = value;
                        Navigator.pop(context);
                                                    });
                                                  }),
                                            ],
                                          ),
                                        )
                                      ],
                                    ),
                        Padding(
                                padding: const EdgeInsets.symmetric(
                                vertical: 8.0),
                        child: Container(
                                color: Colors.grey[300],
                                height: 1,
                                width:
                        MediaQuery.of(context).size.width,
                                      ),
                                    ),
                        FlatButton(
                                padding: EdgeInsets.fromLTRB(
                                MediaQuery.of(context).size.width /
                                        3,
                                0,
                                MediaQuery.of(context).size.width /
                                        3,
                                0),
                                onPressed: () {
                            database.push().set({
                                    'name': accountread.split(';')[0],
                                    'email': accountread.split(';')[1],
                                    'phone': accountread.split(';')[3],
                                    'totalprice': price.toString(),
                                    'address': accountread.split(';')[5]
                                          });
                            String messages =
                                    'Item                                 Quantity        Price\n \n \n';
                            for (int i = 0;
                                 i < read.split('|').length - 1;
                                 i++) {
                                int itprice = int.parse(read
                                        .split('|')[i]
                                        .split(';')[2]
                                        .substring(3)) *
                                        int.parse(read
                                                .split('|')[i]
                                                .split(';')[4]);
                                messages = messages +
                                        read
                                                .split('|')[i]
                                                .split(';')[0] +
                                        '                     ' +
                                        read
                                                .split('|')[i]
                                                .split(';')[4] +
                                        '      Rs.' +
                                        itprice.toString() +
                                        '\n \n \n';
                                database.push().set({
                                        'Name': read
                                        .split('|')[i]
                                        .split(';')[0],
                                        'Qty': read
                                        .split('|')[i]
                                        .split(';')[4],
                                        'Price':
                                'Rs.' + itprice.toString()
                                            });
                            }
                            messages = messages +
                                    'Total items:' +
                                    totalquantity.toString() +
                                    '                   ' +
                                    'Total Price:' +
                                    price.toString();
                            email(accountread.split(';')[1],
                                    messages);
                            _cart_write('');
                            Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                            builder: (context) =>
                            thanks()));
                        },
                        color: Colors.orangeAccent,
                                textColor: Colors.white,
                                child: Text('Place Order'))
                                  ],
                                ),
                              ),
                            );
                    });
                }
            },
            child: Container(
                    width: MediaQuery.of(context).size.width,
                    decoration: BoxDecoration(
                    color: Colors.white,
                    boxShadow: [
            BoxShadow(color: Colors.grey, blurRadius: 12)
                        ],
            borderRadius:
            BorderRadius.vertical(top: Radius.circular(40))),
            child: Padding(
                    padding: const EdgeInsets.fromLTRB(25, 15, 25, 15),
                    child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
            Text(
                    'Subtotal($totalquantity item)',
                    style: TextStyle(
                    fontSize: 20,
                            ),
                          ),
            Text(
                    'Rs.$price',
                    style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              )
            ],
          ),
        ),
      );
        }
    }
}

class wishlist extends StatefulWidget {
    @override
    _wishlistState createState() => _wishlistState();
}

class _wishlistState extends State<wishlist> {
    String read = '';
    String cartread = '';
    _wishlistState() {
        _wishlist_read().then((value) => setState(() {
            read = value;
        }));
        _cart_read().then((value) => setState(() {
            cartread = value;
        }));
    }
    @override
    Widget build(BuildContext context) {
        final key = new GlobalKey<ScaffoldState>();
        if (read == '') {
            return Scaffold(
                    body: SafeArea(
                    child: Container(
                    color: Colors.white,
                    child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.only(top: 30),
            child: Image.asset('images/wishlist.jpg'),
              ),
            Column(
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.only(bottom: 10.0),
            child: Text(
                    'Your wishlist is empty',
                    style: TextStyle(
                    color: Colors.black,
                    fontSize: 25,
                    fontWeight: FontWeight.bold),
                    ),
                  ),
            Padding(
                    padding: const EdgeInsets.only(bottom: 25.0),
            child: Text(
                    'Saves the products you are interested in here',
                    style: TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.grey[400],
                    fontSize: 15),
                    ),
                  ),
            Padding(
                    padding: const EdgeInsets.only(bottom: 80.0),
            child: FlatButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (content) => MyApp()));
            },
            color: Colors.orangeAccent,
                    textColor: Colors.white,
                    child: Text(
                    "Continue Shopping",
                    style:
            TextStyle(fontSize: 20, fontFamily: 'MeriendaOne'),
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        )),
            bottomNavigationBar: BottomAppBar(
                    color: Colors.white,
                    child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 15.0),
            child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => MyApp()));
            },
            icon: Icon(
                    Icons.home,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => wishlist()));
            },
            icon: Icon(
                    Icons.favorite,
                    size: 40.0,
                    color: Colors.orangeAccent,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => cart()));
            },
            icon: Icon(
                    Icons.shopping_cart,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => account()));
            },
            icon: Icon(
                    Icons.account_circle,
                    size: 40.0,
                  ),
                ),
              ],
            ),
          ),
        ),
      );
        } else {
            return Scaffold(
                    key: key,
                    bottomNavigationBar: BottomAppBar(
                    color: Colors.white,
                    child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 15.0),
            child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => MyApp()));
            },
            icon: Icon(
                    Icons.home,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => wishlist()));
            },
            icon: Icon(
                    Icons.favorite,
                    size: 40.0,
                    color: Colors.orangeAccent,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => cart()));
            },
            icon: Icon(
                    Icons.shopping_cart,
                    size: 40.0,
                  ),
                ),
            IconButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (context) => account()));
            },
            icon: Icon(
                    Icons.account_circle,
                    size: 40.0,
                  ),
                ),
              ],
            ),
          ),
        ),
            body: SafeArea(
                    child: ListView.builder(
                    itemCount: read.split('|').length - 1,
                    itemBuilder: (BuildContext ctx, int index) {
                return InkWell(
                        onTap: () {
                    Navigator.push(
                            context,
                            MaterialPageRoute(
                                    builder: (context) => item(
                            itemn: Itemn(
                            read.split('|')[index].split(';')[0],
                            read.split('|')[index].split(';')[3],
                            read.split('|')[index].split(';')[2],
                            read.split('|')[index].split(';')[1]))));
                },
                child: Column(
                        children: <Widget>[
                Row(
                        children: <Widget>[
                Expanded(
                        flex: 1,
                        child: Image.network(
                        read.split('|')[index].split(';')[1],
                        fit: BoxFit.fitHeight,
                                )),
                Expanded(
                        flex: 2,
                        child: Padding(
                        padding: const EdgeInsets.only(left: 10.0),
                child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                Padding(
                        padding: const EdgeInsets.only(top: 10.0),
                child: Text(
                        read.split('|')[index].split(';')[0],
                        style: TextStyle(
                        fontWeight: FontWeight.bold),
                                      ),
                                    ),
                RichText(
                        text: TextSpan(
                        text: 'Unit Price:',
                        style: TextStyle(color: Colors.black),
                children: <TextSpan>[
                TextSpan(
                        text: read
                        .split('|')[index]
                        .split(';')[2],
                        style: TextStyle(
                        color: Colors.black,
                        fontWeight:
                FontWeight.bold)),
                                          ]),
                                    ),
                Padding(
                        padding:
                                          const EdgeInsets.only(bottom: 5.0),
                child: Row(
                        mainAxisAlignment:
                MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                ButtonTheme(
                        materialTapTargetSize:
                MaterialTapTargetSize
                        .shrinkWrap,
                        child: OutlineButton(
                        padding: EdgeInsets.all(5),
                        shape: RoundedRectangleBorder(
                        borderRadius:
                BorderRadius.circular(
                        20)),
                borderSide: BorderSide(
                        color: Colors.orangeAccent),
                textColor: Colors.orangeAccent,
                        child: Text('Add to Cart'),
                        onPressed: () {
                    var duplicate = false;
                    for (var k = 0;
                         k <
                                 cartread
                                         .split('|')
                                         .length;
                         k++) {
                        if (cartread
                                .split('|')[k]
                                .split(';')[0] ==
                                read
                                        .split('|')[index]
                                        .split(';')[0]) {
                            key.currentState
                                    .showSnackBar(SnackBar(
                                            content:
                            Text('Added to cart'),
                                    action: SnackBarAction(
                                    label: 'Go to Cart',
                                    onPressed: () {
                                Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                                builder:
                                (content) =>
                                cart()));
                            }),
                                                    ));
                            duplicate = true;
                            int newquant = int.parse(
                                    cartread
                                            .split('|')[k]
                                            .split(
                                                    ';')[4]) +
                                    1;
                            _cart_write(cartread.replaceAll(
                                    read.split('|')[index].split(';')[0] +
                                            ';' +
                                            read
                                                    .split(
                                                            '|')[index]
                                                    .split(';')[1] +
                                            ';' +
                                            read
                                                    .split(
                                                            '|')[index]
                                                    .split(';')[2] +
                                            ';' +
                                            read
                                                    .split(
                                                            '|')[index]
                                                    .split(';')[3] +
                                            ';' +
                                            cartread
                                                    .split('|')[k]
                                                    .split(';')[4] +
                                            '|',
                                    read.split('|')[index].split(
                                            ';')[0] +
                                            ';' +
                                            read
                                                    .split(
                                                            '|')[index]
                                                    .split(';')[1] +
                                            ';' +
                                            read
                                                    .split(
                                                            '|')[index]
                                                    .split(';')[2] +
                                            ';' +
                                            read
                                                    .split('|')[index]
                                                    .split(';')[3] +
                                            ';' +
                                            newquant.toString() +
                                            '|'));
                        }
                    }
                    if (duplicate == false) {
                        _cart_write(cartread +
                                read
                                        .split('|')[index]
                                        .split(';')[0] +
                                ';' +
                                read
                                        .split('|')[index]
                                        .split(';')[1] +
                                ';' +
                                read
                                        .split('|')[index]
                                        .split(';')[2] +
                                ';' +
                                read
                                        .split('|')[index]
                                        .split(';')[3] +
                                ';' +
                                '1' +
                                '|');
                        key.currentState.showSnackBar(
                                SnackBar(
                                        content: Text(
                                'Added to cart'),
                                action: SnackBarAction(
                                label: 'Go to Cart',
                                onPressed: () {
                            Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                            builder:
                            (content) =>
                            cart()));
                        })));
                    }
                },
                                            ),
                                          ),
                Padding(
                        padding: const EdgeInsets.only(
                        right: 15.0),
                child: IconButton(
                        onPressed: () {
                    _wishlist_write(
                            read.replaceFirst(
                                    read.split('|')[index] +
                                            '|',
                                    ''));
                    setState(() {
                        read = read.replaceFirst(
                                read.split('|')[index] +
                                        '|',
                                '');
                    });
                },
                icon: Icon(Icons.delete,
                        color: Colors.red),
                                            ),
                                          )
                                        ],
                                      ),
                                    )
                                  ],
                                ),
                              ),
                            ),
                          ],
                        ),
                Container(
                        height: 1,
                        width: MediaQuery.of(context).size.width,
                        color: Colors.grey.shade300,
                        ),
                      ],
                    ),
                  );
            })),
      );
        }
    }
}

class search extends StatefulWidget {
    final Search searchitems;
    search({Key key, @required this.searchitems}) : super(key: key);

    @override
    _searchState createState() => _searchState();
}

class _searchState extends State<search> {
    List<Itemn> items = [];
    @override
    void initState() {
        // TODO: implement initState
        super.initState();
        DatabaseReference databaseReference =
                FirebaseDatabase.instance.reference().child('products');
        databaseReference.once().then((DataSnapshot snap) {
            var keys = snap.value.keys;
            var data = snap.value;

            if (widget.searchitems.input == 'Fruits and Vegetables') {
                for (var key in keys) {
                    if (data[key]['category'] == 'Fruits and Vegetables') {
                        setState(() {
                            items.add(Itemn(data[key]['name'], 'description',
                                    'Rs.' + data[key]['price'], data[key]['imgurl']));
                        });
                    }
                }
            }
            if (widget.searchitems.input == 'Staples and Household items') {
                for (var key in keys) {
                    if (data[key]['category'] == 'Staples and Household items') {
                        setState(() {
                            items.add(Itemn(data[key]['name'], 'description',
                                    'Rs.' + data[key]['price'], data[key]['imgurl']));
                        });
                    }
                }
            }
            if (widget.searchitems.input == 'Beverages and Snacks') {
                for (var key in keys) {
                    if (data[key]['category'] == 'Beverages and Snacks') {
                        setState(() {
                            items.add(Itemn(data[key]['name'], 'description',
                                    'Rs.' + data[key]['price'], data[key]['imgurl']));
                        });
                    }
                }
            } else {
                for (var key in keys) {
                    bool notcontain = false;
                    for (int i = 0; i < widget.searchitems.input.length; i++) {
                        if (data[key]['name'].toString().toLowerCase().contains(widget
                                .searchitems.input
                                .toLowerCase()
                                .substring(i, i + 1)) ==
                                false) {
                            notcontain = true;
                        }
                    }
                    if (notcontain == false) {
                        setState(() {
                            items.add(Itemn(data[key]['name'], data[key]['description'],
                                    'Rs.' + data[key]['price'], data[key]['imgurl']));
                        });
                    }
                }
            }
        });
    }

    @override
    Widget build(BuildContext context) {
        if (items.length == 0 || widget.searchitems.input.trim() == '') {
            return Scaffold(
                    appBar: AppBar(
                    backgroundColor: Colors.orangeAccent,
                    leading: IconButton(
                    icon: Icon(Icons.arrow_back, color: Colors.white),
            onPressed: () => Navigator.of(context).pop(),
          ),
            title: Text(widget.searchitems.input),
                    centerTitle: true,
        ),
            body: SafeArea(
                    child: Container(
                    color: Colors.white,
                    child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.only(top: 30),
            child: Image.asset('images/Package_box_brown.jpg'),
              ),
            Column(
                    children: <Widget>[
            Padding(
                    padding: const EdgeInsets.only(bottom: 10.0),
            child: Text(
                    'No items found',
                    style: TextStyle(
                    color: Colors.black,
                    fontSize: 25,
                    fontWeight: FontWeight.bold),
                    ),
                  ),
            Padding(
                    padding: const EdgeInsets.only(bottom: 25.0),
            child: Text(
                    'This product does not exist or is out of stock',
                    style: TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.grey[400],
                    fontSize: 15),
                    ),
                  ),
            Padding(
                    padding: const EdgeInsets.only(bottom: 80.0),
            child: FlatButton(
                    onPressed: () {
                Navigator.push(context,
                        MaterialPageRoute(builder: (content) => MyApp()));
            },
            color: Colors.orangeAccent,
                    textColor: Colors.white,
                    child: Text(
                    "Continue Shopping",
                    style:
            TextStyle(fontSize: 20, fontFamily: 'MeriendaOne'),
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        )),
      );
        } else {
            return Scaffold(
                    appBar: AppBar(
                    backgroundColor: Colors.orangeAccent,
                    leading: IconButton(
                    icon: Icon(Icons.arrow_back, color: Colors.white),
            onPressed: () => Navigator.of(context).pop(),
          ),
            title: Text(widget.searchitems.input),
                    centerTitle: true,
        ),
            body: SafeArea(
                    child: ListView.builder(
                    itemCount: items.length,
                    itemBuilder: (BuildContext cxt, int index) {
                return InkWell(
                        onTap: () {
                    Navigator.push(
                            context,
                            MaterialPageRoute(
                                    builder: (context) => item(itemn: items[index])));
                },
                child: Container(
                        child: Column(
                        mainAxisSize: MainAxisSize.max,
                        children: <Widget>[
                Padding(
                        padding: const EdgeInsets.symmetric(vertical: 10.0),
                child: Row(
                        children: <Widget>[
                Expanded(
                        flex: 1,
                        child: Padding(
                        padding: const EdgeInsets.only(left: 8.0),
                child: Image.network(
                        items[index].imgurl,
                        fit: BoxFit.fitHeight,
                                    ),
                                  )),
                Expanded(
                        flex: 2,
                        child: Padding(
                        padding:
                                      const EdgeInsets.fromLTRB(10.0, 0, 0, 5),
                        child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment:
                CrossAxisAlignment.start,
                        children: <Widget>[
                Text(
                        items[index].name,
                        style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold),
                                      ),
                RichText(
                        text: TextSpan(
                        text: 'Unit Price:',
                        style:
                TextStyle(color: Colors.black),
                children: <TextSpan>[
                TextSpan(
                        text: items[index].price,
                        style: TextStyle(
                        color: Colors.black,
                        fontWeight:
                FontWeight.bold)),
                                            ]),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                Container(
                        height: 1,
                        width: MediaQuery.of(context).size.width,
                        color: Colors.grey.shade300,
                        ),
                      ],
                    ),
                  ),
                );
            }),
        ),
      );
        }
    }
}

class thanks extends StatelessWidget {
    @override
    Widget build(BuildContext context) {
        return Scaffold(
                body: SafeArea(
                child: Container(
                color: Colors.white,
                child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(top: 30),
        child: Image.asset('images/thanks.jpg'),
            ),
        Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
        Padding(
                padding: const EdgeInsets.only(bottom: 10.0),
        child: Text(
                'Your order was placed successfully',
                style: TextStyle(
                color: Colors.black,
                fontSize: 25,
                fontWeight: FontWeight.bold),
                  ),
                ),
        Padding(
                padding: const EdgeInsets.only(bottom: 25.0),
        child: Text(
                'You shall recieve an email confirming that your order has been placed',
                textAlign: TextAlign.center,
                style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.grey[400],
                fontSize: 15),
                  ),
                ),
        Padding(
                padding: const EdgeInsets.only(bottom: 80.0),
        child: FlatButton(
                onPressed: () {
            Navigator.push(context,
                    MaterialPageRoute(builder: (content) => MyApp()));
        },
        color: Colors.orangeAccent,
                textColor: Colors.white,
                child: Text(
                "Continue Shopping",
                style: TextStyle(fontSize: 20, fontFamily: 'MeriendaOne'),
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      )),
    );
    }
}
