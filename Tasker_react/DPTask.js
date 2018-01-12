import React from 'react';
import { StyleSheet, View, TouchableHighlight, Text,  ScrollView,
  KeyboardAvoidingView, TextInput, TouchableOpacity,Dimensions ,
  DatePickerAndroid,Picker,Linking, AsyncStorage } from 'react-native';
import Task from './Task';
import {ref,firebaseAuth} from './Firebasedb';

export class DPTask extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  constructor(props) {
    super(props)
    console.log('----------------DP_TASK--------------------:entering');
    var obj=this.props.navigation.state.params.o;
    console.log('----------------DP_TASK--------------------:paased');
    console.log('----------------DP_TASK--------------------:em '+p.name);
    this.state = {
      ddate:obj.deadline,
      Taskname:obj.name,
      Taskdescription:obj.description,
      employer:'',
      loading:true
    }
    this.task=ref.child("Tasks").child(obj.idt);
    var that=this;
    this.on('value',(dataSnapshoot)=>{
      if(dataSnapshoot.exits()){
      var t=dataSnapshoot.val();
      that.setState({ddate:t.deadline,Taskdescription:t.description});
      var p=ref.child('Persons').child(t.idp).child('name');
      p.once('value').then((datasnapShoo)=>{
        if(datasnapShoo.exits())
          that.setState({employer:datasnapShoo.val(),loading:false});
      });
    }
    });
  }

  render() {
    if(this.state.loading)
    return(<View>loading...</View>);
    const {navigate}=this.props.navigation;
    return (
        < ScrollView style={styles.wrapper}>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Task Name:</Text>
          <Text style={styles.input}>{this.state.Taskname}</Text>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Description:</Text>
          <Text style={styles.input}>{this.state.Taskdescription}</Text>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Deadline:</Text>
          <Text style={styles.input}>{this.state.ddate.getMonth()+'/'+this.state.ddate.getDate()+'/'+
          this.state.ddate.getFullYear()}</Text>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Employer:</Text>
          <Text style={styles.input}>{this.state.employer}</Text>
        </View>
        </ ScrollView>
    );
  }
}


let winSize = Dimensions.get('window');

const styles = StyleSheet.create({
  wrapper: {
     backgroundColor: '#4c69a5',
     height: winSize.height,
     flex:1
   },
   formContainner:{
     alignSelf:'stretch',

   },
   text:{
    textAlign:'left',
    fontWeight :'bold',
    minWidth : 50,
    fontSize:18,
   },
  button:
  {
    padding: 10, 
    alignItems:'center',
    justifyContent:'center',
    borderRadius: 20,
    
  },
  cont:{
    borderRadius: 5,
    alignItems: 'center',
    justifyContent: 'center',
  },
  input:{
    margin: 25
  }
});