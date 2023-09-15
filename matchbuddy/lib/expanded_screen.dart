import 'package:flutter/material.dart';

class Expanded_screen extends StatelessWidget {
  final text, index;
  const Expanded_screen({Key? key, this.text, this.index}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Hero(
              tag: index,
              child: Material(
                child: Text(
                  text,
                  style: TextStyle(fontWeight: FontWeight.w300),
                ),
              )),
        ),
      ),
    );
  }
}
