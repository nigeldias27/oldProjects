import 'dart:io';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:location/location.dart';

_write(String text) async {
  final Directory directory = await getApplicationDocumentsDirectory();
  final File file = File('${directory.path}/my_file.txt');
  await file.writeAsString(text);
}

class order {
  final String name;
  final String address;
  final String phone;
  final String email;
  final String payment;
  final String totalprice;
  final List orders;

  order(this.name, this.email, this.address, this.phone, this.payment,
      this.totalprice, this.orders);
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

Future getUserLocation() async {
  Location location = new Location();

  bool _serviceEnabled;
  PermissionStatus _permissionGranted;

  _serviceEnabled = await location.serviceEnabled();
  if (!_serviceEnabled) {
    _serviceEnabled = await location.requestService();
    if (!_serviceEnabled) {
      return null;
    }
  }

  _permissionGranted = await location.hasPermission();
  if (_permissionGranted == PermissionStatus.denied) {
    _permissionGranted = await location.requestPermission();
    if (_permissionGranted != PermissionStatus.granted) {
      return null;
    }
  }
  final result = await location.getLocation();
  return result;
}

void main() {
  runApp(MaterialApp(
    home: Login(),
  ));
  ErrorWidget.builder = (FlutterErrorDetails details) => Container(
        color: Colors.white,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              'No orders have been place yet',
              style:
                  TextStyle(fontWeight: FontWeight.bold, color: Colors.black45),
            ),
          ],
        ),
      );
}

class MyApp extends StatefulWidget {
  final String email;
  MyApp({Key key, @required this.email}) : super(key: key);
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List ord = [];
  _MyAppState() {
    DatabaseReference firebase =
        FirebaseDatabase.instance.reference().child('orders');
    firebase.once().then((DataSnapshot snap) {
      var keys = snap.value.keys;
      var data = snap.value;
      print(keys);
      for (var keyl in keys) {
        var val = data[keyl].keys;
        print(val);
        int iteration = 0;
        String name = '';
        String address = '';
        String phone = '';
        String email = '';
        String payment = '';
        String totalprice = '';
        List or = [];
        for (var key2 in val) {
          if (iteration == 0) {
            if (widget.email == data[keyl][key2]['email']) {
              print(data[keyl][key2]['Name']);
              or.add([
                data[keyl][key2]['Name'],
                data[keyl][key2]['Price'],
                data[keyl][key2]['Qty'],
                key2,
                keyl
              ]);
            }
          } else {
            print(data[keyl][key2]);
            name = data[keyl][key2]['name'];
            print(data[keyl][key2]);
            address = data[keyl][key2]['address'];
            print(address);
            phone = data[keyl][key2]['phone'];
            email = data[keyl][key2]['email'];
            payment = data[keyl][key2]['payment'];
            totalprice = data[keyl][key2]['totalprice'];
          }
          iteration++;
        }
        setState(() {
          ord.add(order(name, email, address, phone, payment, totalprice, or));
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    print(ord[0].orders);

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
                  Icons.list,
                  color: Colors.orangeAccent,
                  size: 40.0,
                ),
              ),
              IconButton(
                onPressed: () {
                  Navigator.push(
                      context, MaterialPageRoute(builder: (context) => add()));
                },
                icon: Icon(
                  Icons.add,
                  size: 40.0,
                ),
              ),
              IconButton(
                onPressed: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => remove()));
                },
                icon: Icon(
                  Icons.remove,
                  size: 40.0,
                ),
              ),
            ],
          ),
        ),
      ),
      body: SafeArea(
          child: ListView.builder(
              itemCount: ord.length,
              itemBuilder: (BuildContext context, int index) {
                return Column(
                  children: [
                    InkWell(
                      onTap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) =>
                                    ordera(orders: ord[index])));
                      },
                      child: Row(
                        children: [
                          Expanded(
                              flex: 1,
                              child: Text(
                                ord[index].name,
                                style: TextStyle(
                                    fontWeight: FontWeight.bold, fontSize: 15),
                              )),
                          Expanded(
                              flex: 2,
                              child: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                      flex: 1,
                                      child: Column(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.start,
                                        children: [
                                          Text("Phone:" + ord[index].phone),
                                          Text("Email:" + ord[index].email),
                                          Text("Payment:" + ord[index].payment),
                                        ],
                                      )),
                                  Expanded(
                                      flex: 1,
                                      child: Text(
                                        "Total Price:" + ord[index].totalprice,
                                        textAlign: TextAlign.right,
                                      )),
                                ],
                              )),
                        ],
                      ),
                    ),
                    Container(
                      width: MediaQuery.of(context).size.width,
                      color: Colors.grey,
                      height: 1,
                    ),
                  ],
                );
              })),
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
                    'Groceries4Everyone',
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.orangeAccent,
                        fontSize: 40,
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
                            MaterialPageRoute(builder: (context) => add()));
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
      resizeToAvoidBottomInset: false,
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

                  Navigator.push(
                      context, MaterialPageRoute(builder: (context) => add()));
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
  LocationData valu;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getUserLocation().then((value) => setState(() {
          print(value);
          valu = value;
        }));
  }

  @override
  Widget build(BuildContext context) {
    final key = new GlobalKey<ScaffoldState>();
    return Scaffold(
      resizeToAvoidBottomInset: false,
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
                    if (valu != null) {
                      database.push().set({
                        'email': email.text,
                        'name': name.text,
                        'password': password.text,
                        'phone': phone.text,
                        'lat': valu.latitude.toString(),
                        'long': valu.longitude.toString()
                      });
                      Navigator.push(context,
                          MaterialPageRoute(builder: (context) => SignIn()));
                    } else {
                      key.currentState.showSnackBar(SnackBar(
                          content: Text(
                              'Please go to Settings >App & Notitfication>Groceries4Everyone>Enable location')));
                    }
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
                      "Let's get started. Ensure that you are in your shop while registering.",
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

class add extends StatefulWidget {
  @override
  _addState createState() => _addState();
}

class _addState extends State<add> {
  String read = '';

  final key1 = GlobalKey<EditableTextState>();
  final key2 = GlobalKey<EditableTextState>();
  final key3 = GlobalKey<EditableTextState>();
  final key4 = GlobalKey<EditableTextState>();
  _addState() {
    _read().then((value) => setState(() {
          read = value.split(';')[1];
        }));
  }
  int _value = 1;
  TextEditingController name = TextEditingController();
  TextEditingController price = TextEditingController();
  TextEditingController imgurl = TextEditingController();
  TextEditingController description = TextEditingController();
  DatabaseReference database =
      FirebaseDatabase.instance.reference().child('products').push();
  @override
  Widget build(BuildContext context) {
    final key = GlobalKey<ScaffoldState>();
    return Scaffold(
      key: key,
      resizeToAvoidBottomInset: false,
      bottomNavigationBar: BottomAppBar(
        color: Colors.white,
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 15.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              IconButton(
                onPressed: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => MyApp(
                                email: read,
                              )));
                },
                icon: Icon(
                  Icons.list,
                  size: 40.0,
                ),
              ),
              IconButton(
                onPressed: () {},
                icon: Icon(
                  Icons.add,
                  color: Colors.orangeAccent,
                  size: 40.0,
                ),
              ),
              IconButton(
                onPressed: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) => remove()));
                },
                icon: Icon(
                  Icons.remove,
                  size: 40.0,
                ),
              ),
            ],
          ),
        ),
      ),
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "Add products",
              style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 20,
                  fontFamily: 'MeriendaOne'),
            ),
            Padding(
              padding: const EdgeInsets.all(15.0),
              child: TextField(
                key: key1,
                controller: name,
                decoration: InputDecoration(
                  labelStyle: TextStyle(color: Colors.grey),
                  hintText: 'Name',
                  focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.orangeAccent)),
                  border: OutlineInputBorder(borderSide: BorderSide()),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(15.0),
              child: TextField(
                key: key2,
                controller: price,
                decoration: InputDecoration(
                  labelStyle: TextStyle(color: Colors.grey),
                  hintText: 'Price',
                  focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.orangeAccent)),
                  border: OutlineInputBorder(borderSide: BorderSide()),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(15.0),
              child: TextField(
                key: key3,
                controller: imgurl,
                decoration: InputDecoration(
                  labelStyle: TextStyle(color: Colors.grey),
                  hintText: 'Image Url',
                  focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.orangeAccent)),
                  border: OutlineInputBorder(borderSide: BorderSide()),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(15.0),
              child: TextField(
                key: key4,
                controller: description,
                maxLines: 5,
                decoration: InputDecoration(
                  labelStyle: TextStyle(color: Colors.grey),
                  hintText: 'Description',
                  focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.orangeAccent)),
                  border: OutlineInputBorder(borderSide: BorderSide()),
                ),
              ),
            ),
            DropdownButton(
                value: _value,
                items: [
                  DropdownMenuItem(
                    child: Text("Fruits and Vegetables"),
                    value: 1,
                  ),
                  DropdownMenuItem(
                    child: Text("Staples and Household items"),
                    value: 2,
                  ),
                  DropdownMenuItem(
                    child: Text("Beverages and Snacks"),
                    value: 3,
                  ),
                ],
                onChanged: (value) {
                  setState(() {
                    _value = value;
                  });
                }),
            FlatButton(
                padding: EdgeInsets.fromLTRB(
                    MediaQuery.of(context).size.width / 3,
                    0,
                    MediaQuery.of(context).size.width / 3,
                    0),
                onPressed: () {
                  if (name.text.trim() == '' ||
                      price.text.trim() == '' ||
                      description.text.trim() == '' ||
                      imgurl.text.trim() == '') {
                    key.currentState.showSnackBar(
                        SnackBar(content: Text("Please fill all the fields")));
                  } else {
                    if (_value == 1) {
                      database.set({
                        'name': name.text,
                        'price': price.text,
                        'imgurl': imgurl.text,
                        'description': description.text,
                        'category': 'Fruits and Vegetables',
                        'email': read
                      });
                    } else if (_value == 2) {
                      database.set({
                        'name': name.text,
                        'price': price.text,
                        'imgurl': imgurl.text,
                        'description': description.text,
                        'category': 'Staples and Household items',
                        'email': read
                      });
                    } else {
                      database.set({
                        'name': name.text,
                        'price': price.text,
                        'imgurl': imgurl.text,
                        'description': description.text,
                        'category': 'Beverages and Snacks',
                        'email': read
                      });
                    }
                    key.currentState.showSnackBar(SnackBar(
                      content: Text("Product added"),
                    ));
                  }
                },
                color: Colors.orangeAccent,
                textColor: Colors.white,
                child: Text('ADD')),
          ],
        ),
      ),
    );
  }
}

class remove extends StatefulWidget {
  @override
  _removeState createState() => _removeState();
}

class _removeState extends State<remove> {
  TextEditingController name = TextEditingController();
  DatabaseReference firebase =
      FirebaseDatabase.instance.reference().child('products');
  String email = '';
  _removeState() {
    _read().then((value) => setState(() {
          email = value.split(';')[1];
        }));
  }
  @override
  Widget build(BuildContext context) {
    final key1 = GlobalKey<ScaffoldState>();
    return Scaffold(
      key: key1,
      resizeToAvoidBottomInset: false,
      bottomNavigationBar: BottomAppBar(
        color: Colors.white,
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 15.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              IconButton(
                onPressed: () {
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => MyApp(
                                email: email,
                              )));
                },
                icon: Icon(
                  Icons.list,
                  size: 40.0,
                ),
              ),
              IconButton(
                onPressed: () {
                  Navigator.push(
                      context, MaterialPageRoute(builder: (context) => add()));
                },
                icon: Icon(
                  Icons.add,
                  size: 40.0,
                ),
              ),
              IconButton(
                onPressed: () {},
                icon: Icon(
                  Icons.remove,
                  color: Colors.orangeAccent,
                  size: 40.0,
                ),
              ),
            ],
          ),
        ),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            "Remove product",
            style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 20,
                fontFamily: 'MeriendaOne'),
          ),
          Padding(
            padding: const EdgeInsets.all(15.0),
            child: TextField(
              controller: name,
              decoration: InputDecoration(
                labelStyle: TextStyle(color: Colors.grey),
                hintText: 'Name',
                focusedBorder: OutlineInputBorder(
                    borderSide: BorderSide(color: Colors.orangeAccent)),
                border: OutlineInputBorder(borderSide: BorderSide()),
              ),
            ),
          ),
          FlatButton(
              padding: EdgeInsets.fromLTRB(
                  MediaQuery.of(context).size.width / 3,
                  0,
                  MediaQuery.of(context).size.width / 3,
                  0),
              onPressed: () {
                bool remo = false;
                firebase.once().then((DataSnapshot snap) {
                  var keys = snap.value.keys;
                  var data = snap.value;

                  for (var key in keys) {
                    if (name.text == data[key]['name'] &&
                        email == data[key]['email']) {
                      firebase.child(key).remove();
                      remo = true;
                      key1.currentState.showSnackBar(
                          SnackBar(content: Text('Product Removed')));
                    }
                  }
                });
                if (remo == false) {
                  key1.currentState.showSnackBar(
                      SnackBar(content: Text('No such product found')));
                }
              },
              color: Colors.orangeAccent,
              textColor: Colors.white,
              child: Text(''
                  'REMOVE')),
        ],
      ),
    );
  }
}

class ordera extends StatelessWidget {
  final order orders;
  ordera({Key key, @required this.orders}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    print(orders.orders);
    return Scaffold(
      bottomNavigationBar: BottomAppBar(
        child: ButtonTheme(
          materialTapTargetSize: MaterialTapTargetSize.shrinkWrap,
          child: FlatButton(
              padding: EdgeInsets.symmetric(vertical: 15),
              onPressed: () {
                DatabaseReference firebase =
                    FirebaseDatabase.instance.reference().child('orders');
                for (var order1 in orders.orders) {
                  firebase.child(order1[4]).child(order1[3]).remove();
                  firebase.child(order1[4]).once().then((DataSnapshot snap) {
                    int keys = snap.value.keys.length;
                    if (keys == 1) {
                      firebase.child(order1[4]).remove();
                    }
                  });
                }
                Navigator.push(
                    context, MaterialPageRoute(builder: (context) => yay()));
              },
              color: Colors.orangeAccent,
              child: Text(
                'Finished',
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 20,
                    fontFamily: 'MeriendaOne'),
              )),
        ),
      ),
      body: SafeArea(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                'Address:' + orders.address,
                style: TextStyle(
                    fontWeight: FontWeight.bold, fontFamily: 'MeriendaOne'),
              ),
            ),
            Row(
              children: [
                Expanded(
                    flex: 4,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 5.0),
                      child: Text(
                        'Item Name',
                        style: TextStyle(fontWeight: FontWeight.bold),
                      ),
                    )),
                Expanded(
                    flex: 1,
                    child: Text(
                      'Quantity',
                      textAlign: TextAlign.center,
                      style: TextStyle(fontWeight: FontWeight.bold),
                    )),
                Expanded(
                    flex: 1,
                    child: Text(
                      'Price',
                      textAlign: TextAlign.center,
                      style: TextStyle(fontWeight: FontWeight.bold),
                    )),
              ],
            ),
            SizedBox(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 200,
              child: ListView.builder(
                scrollDirection: Axis.vertical,
                itemCount: orders.orders.length,
                itemBuilder: (BuildContext context, int index) {
                  return InkWell(
                    onTap: () {},
                    child: Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.symmetric(vertical: 8.0),
                          child: Row(
                            children: [
                              Expanded(
                                  flex: 4,
                                  child: Padding(
                                    padding: const EdgeInsets.only(left: 5.0),
                                    child: Text(
                                      orders.orders[index][0],
                                      style: TextStyle(fontSize: 20),
                                    ),
                                  )),
                              Expanded(
                                  flex: 1,
                                  child: Text(
                                    orders.orders[index][2],
                                    textAlign: TextAlign.center,
                                    style: TextStyle(fontSize: 20),
                                  )),
                              Expanded(
                                  flex: 1,
                                  child: Text(
                                    orders.orders[index][1],
                                    textAlign: TextAlign.center,
                                    style: TextStyle(fontSize: 20),
                                  )),
                            ],
                          ),
                        ),
                        Container(
                          color: Colors.grey,
                          height: 1,
                          width: MediaQuery.of(context).size.width,
                        ),
                      ],
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class yay extends StatefulWidget {
  @override
  _yayState createState() => _yayState();
}

class _yayState extends State<yay> {
  String email = '';
  _yayState() {
    _read().then((value) => setState(() {
          email = value.split(';')[1];
        }));
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
          child: Container(
        color: Colors.white,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.network(
                'https://previews.123rf.com/images/timonko/timonko1803/timonko180300169/97154470-hip-hooray-vector-text-greeting-and-birthday-card-a-phrase-for-celebrations-and-congratulations-vect.jpg'),
            Column(
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.only(bottom: 10.0),
                  child: Padding(
                    padding: const EdgeInsets.only(top: 20.0),
                    child: Text(
                      'This order has been finished',
                      style: TextStyle(
                          color: Colors.black,
                          fontSize: 25,
                          fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(bottom: 80.0),
                  child: FlatButton(
                    onPressed: () {
                      Navigator.push(context,
                          MaterialPageRoute(builder: (content) => add()));
                    },
                    color: Colors.orangeAccent,
                    textColor: Colors.white,
                    child: Text(
                      "Go back to the app",
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
