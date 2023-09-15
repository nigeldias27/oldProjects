import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';
import 'dart:io';
import 'package:swipe/swipe.dart';
import 'expanded_screen.dart';
import 'scale_transition.dart';

_write(String text) async {
  final Directory directory = await getApplicationDocumentsDirectory();
  final File file = File('${directory.path}/my_file.txt');
  final filetext = await _read();
  await file.writeAsString(text + '|' + filetext);
}

_rem(String text) async {
  final Directory directory = await getApplicationDocumentsDirectory();
  final File file = File('${directory.path}/my_file.txt');
  await file.writeAsString(text);
}

Future<String> _read() async {
  String text = '';
  try {
    final Directory directory = await getApplicationDocumentsDirectory();
    final File file = File('${directory.path}/my_file.txt');
    text = await file.readAsString();
  } catch (e) {
    print("Couldn't read file");
  }
  return text;
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  TextEditingController notes = TextEditingController();
  final GlobalKey<SliverAnimatedListState> key =
      GlobalKey<SliverAnimatedListState>();
  List<String> some_text = [];

  @override
  void initState() {
    _controller = AnimationController(vsync: this);
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.deepPurple[200],
      body: CustomScrollView(
        slivers: [
          SliverAppBar(
            backgroundColor: Colors.deepPurple[200],
            expandedHeight: MediaQuery.of(context).size.height * 0.25,
            flexibleSpace: FlexibleSpaceBar(
              background: Stack(children: [
                Column(
                  children: [
                    Container(
                      width: MediaQuery.of(context).size.width,
                      height: MediaQuery.of(context).size.height * 0.25,
                      decoration: BoxDecoration(
                          color: Colors.deepPurple[800],
                          borderRadius: BorderRadius.only(
                              bottomLeft: Radius.circular(36),
                              bottomRight: Radius.circular(36))),
                    ),
                  ],
                ),
                Positioned(
                    left: 0,
                    right: 0,
                    top: MediaQuery.of(context).size.height * 0.1,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 18.0),
                      child: Text(
                        'Note app',
                        style: TextStyle(
                            color: Colors.white,
                            fontSize: 32,
                            fontWeight: FontWeight.bold),
                      ),
                    )),
                Positioned(
                    bottom: 10,
                    left: 0,
                    right: 0,
                    child: Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 8.0),
                      child: Container(
                        height: 54,
                        decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.circular(20)),
                        child: Padding(
                          padding: const EdgeInsets.only(left: 32.0),
                          child: Row(
                            children: [
                              Expanded(
                                flex: 3,
                                child: TextField(
                                  controller: notes,
                                  decoration: const InputDecoration(
                                    labelText: 'Note that you want to add',
                                    border: UnderlineInputBorder(
                                        borderSide: BorderSide(
                                            color: Color(0xff564787))),
                                    enabledBorder: UnderlineInputBorder(
                                        borderSide: BorderSide(
                                            color: Color(0xff564787))),
                                    focusedBorder: UnderlineInputBorder(
                                        borderSide: BorderSide(
                                            color: Color(0xff564787))),
                                  ),
                                ),
                              ),
                              Expanded(
                                flex: 1,
                                child: TextButton(
                                    onPressed: () async {
                                      FocusScopeNode currentFocus =
                                          FocusScope.of(context);

                                      if (!currentFocus.hasPrimaryFocus) {
                                        currentFocus.unfocus();
                                      }
                                      print(some_text);
                                      await Future.delayed(
                                          Duration(milliseconds: 650), () {
                                        _write(notes.text.toString());
                                        some_text.insert(
                                            0, notes.text.toString());
                                      });
                                      key.currentState!.insertItem(0,
                                          duration:
                                              Duration(milliseconds: 600));

                                      print(_read());
                                      print(some_text);
                                    },
                                    child: const Icon(
                                      Icons.add,
                                      color: Colors.black,
                                    )),
                              )
                            ],
                          ),
                        ),
                      ),
                    ))
              ]),
            ),
          ),
          FutureBuilder(
            future: _read(),
            builder: (context, AsyncSnapshot snapshot) {
              if (snapshot.hasData) {
                some_text = snapshot.data
                    .split('|')
                    .sublist(0, snapshot.data.split('|').length - 1);
                print(some_text);
                return SliverAnimatedList(
                    key: key,
                    initialItemCount: some_text.length,
                    itemBuilder: (context, index, animation) =>
                        some_text.length != 0
                            ? Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: ScaleTransition(
                                  scale: animation,
                                  child: Swipe(
                                    onSwipeRight: () async {
                                      some_text.removeAt(index);
                                      _rem(some_text.join('|'));
                                      key.currentState!.removeItem(
                                          index,
                                          (context, animation) => Padding(
                                                padding: EdgeInsets.all(8),
                                                child: SlideTransition(
                                                  position: Tween<Offset>(
                                                    begin: const Offset(1, 0),
                                                    end: Offset(0, 0),
                                                  ).animate(animation),
                                                  child: some_widget(
                                                      index: index,
                                                      some_text: some_text),
                                                ),
                                              ));
                                      /*
                                      await Future.delayed(
                                          Duration(milliseconds: 350), () {});
                                      some_text.removeAt(index);
                                      _rem(some_text.join('|'));*/
                                    },
                                    onSwipeLeft: () async {
                                      some_text.removeAt(index);
                                      _rem(some_text.join('|'));
                                      key.currentState!.removeItem(
                                          index,
                                          (context, animation) => Padding(
                                                padding: EdgeInsets.all(8),
                                                child: SlideTransition(
                                                  position: Tween<Offset>(
                                                    begin: const Offset(-1, 0),
                                                    end: Offset(0, 0),
                                                  ).animate(animation),
                                                  child: some_widget(
                                                      index: index,
                                                      some_text: some_text),
                                                ),
                                              ));
                                    },
                                    child: some_widget(
                                        index: index, some_text: some_text),
                                  ),
                                ),
                              )
                            : Padding(
                                padding: EdgeInsets.only(
                                    top: MediaQuery.of(context).size.height *
                                        0.2),
                                child: Column(
                                  children: const [
                                    Icon(
                                      Icons.notes,
                                      color: Colors.white70,
                                      size: 50,
                                    ),
                                    Padding(
                                      padding: EdgeInsets.only(top: 8.0),
                                      child: Text(
                                        'No notes have been added',
                                        style: TextStyle(
                                            color: Colors.white70,
                                            fontSize: 20),
                                      ),
                                    )
                                  ],
                                ),
                              ));
              } else {
                return SliverToBoxAdapter(
                  child: CircularProgressIndicator(),
                );
              }
            },
          ),
        ],
      ),
    );
  }
}

// ignore: camel_case_types
class some_widget extends StatelessWidget {
  final index, some_text;

  const some_widget({Key? key, this.index, this.some_text}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
            context,
            ScaleRoute(
                page: Expanded_screen(
                    text: some_text[index], index: index.toString())));
      },
      child: Container(
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(36),
          color: Colors.white,
        ),
        child: Padding(
          padding: EdgeInsets.symmetric(horizontal: 30.0, vertical: 25),
          child: Hero(
            tag: index.toString(),
            child: Material(
              color: Colors.white,
              child: Text(
                some_text[index],
                style: TextStyle(fontWeight: FontWeight.w300),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
