import React from 'react';
import { StyleSheet, View, TouchableHighlight, Text, ScrollView,
  KeyboardAvoidingView, TextInput, TouchableOpacity,Dimensions ,
  DatePickerAndroid,Picker,Linking, AsyncStorage } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import Task from './Task';
import {DaBe} from './Database';

export class Create_task extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  constructor(props) {
    super(props)
    console.log('----------------CREATE_TASK--------------------:entering');
    var list=DaBe.getPersons();
    console.log('----------------CREATE_TASK--------------------:list:'+list);
    var namelist=new Array();
    namelist.push('All');
    for(var i=0;i<list.length;i++)
      namelist.push(list[i].name);
    this.state = {
      ddate:new Date(2017,1,1),
      Taskname: '',
      Taskdescription:'',
      idp:0,
      name:'All',
      idm:0,
      idt:0,
      persons:list,
      names:namelist
    }


  }
  onSubmit(){
    var id=this.state.idp;
    var emails="";
    msg='Hello there you have a new task:\n'+this.state.Taskname+
    ':\n'+this.state.Taskdescription+'\nDeadline:'+
    this.state.ddate.getMonth()+'/'+this.state.ddate.getDate()+'/'+
    this.state.ddate.getFullYear();
    console.log('----------------CREATE_TASK--------------------:creating');
    if(id!=0){
      DaBe.addTask(new Task(this.state.Taskname,this.state.Taskdescription
            ,0,this.state.idm,this.state.persons[id-1].id,this.state.ddate));
      emails+=this.state.persons[id-1].address;
      }
    else{
      console.log('----------------CREATE_TASK--------------------:all selected');
      for(var i=0;i<this.state.persons.length;i++)
      {
        DaBe.addTask(new Task(this.state.Taskname,this.state.Taskdescription
          ,0,this.state.idm,this.state.persons[i].id,this.state.ddate));
    emails+=this.state.persons[i].address+',';
      }
    }
    console.log('----------------CREATE_TASK--------------------:sending mail');
    Linking.openURL('mailto:'+emails+'?subject=New Task&body='+msg);
  }

  async changeDate(){
    try { 
      const {action, year, month, day} = await DatePickerAndroid.open({ 
        date: new Date(), minDate:new Date() }); 
        if (action !== DatePickerAndroid.dismissedAction) {
          this.setState({ddate:new Date(year,month+1,day)});
        } 
      } catch ({code, message}) { 
        console.warn('Cannot open date picker', message); }
  }
  renderPickerItems(data) {
    const elements = data.map((val, index) => {
        return <Picker.Item key={index} label={val} value={val} /> 
    });
    return elements; 
  }

  render() {
    const {navigate}=this.props.navigation;
    return (
      <KeyboardAwareScrollView style={{ backgroundColor: '#4c69a5' }}
        resetScrollToCoords={{ x: 0, y: 0 }}
        contentContainerStyle={styles. wrapper}
        scrollEnabled={true}>
      <ScrollView style={styles.wrapper}>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Task Name:</Text>
          <TextInput underlineColorAndroid={'black'} style={styles.input}
          onChangeText={(value) => this.setState({Taskname:value})} value={this.state.Taskname}/>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Description:</Text>
          <TextInput underlineColorAndroid={'black'} style={styles.input}
          onChangeText={(value) => this.setState({Taskdescription:value})} value={this.state.Taskdescription}/>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Deadline:</Text>
          <TouchableHighlight style={styles.content} onPress={()=>{this.changeDate()}}>
          <Text style={styles.input}>{this.state.ddate.getMonth()+'/'+this.state.ddate.getDate()+'/'+
          this.state.ddate.getFullYear()}</Text>
        </TouchableHighlight>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Employer:</Text>
          <Picker selectedValue={this.state.name} style={styles.input}
           onValueChange={(itemValue, itemIndex) => this.setState({idp: itemIndex,name:itemValue})}>
            {this.renderPickerItems(this.state.names)}
          </Picker>
        </View>
        <TouchableHighlight style={styles.cont} onPress={()=>{this.onSubmit()}}>
        <View style={styles.button}>
          <Text style={{fontSize:20}}>Submit</Text>
        </View>
      </TouchableHighlight>
      </ScrollView>
      </KeyboardAwareScrollView>
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
     flex:0.21

   },
   text:{
    textAlign:'left',
    fontWeight :'bold',
    minWidth : 50,
    fontSize:18,
    flex:0.5
   },
    content:{
      flex:0.5,
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
    flex:0.7,
    margin: 25
  }
});


/*render() {
    const {navigate}=this.props.navigation;
    return (
      <KeyboardAvoidingView behavior='padding' style={styles.wrapper}>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Task Name:</Text>
          <TextInput underlineColorAndroid={'black'} style={styles.input}
          onChangeText={(value) => this.setState({Taskname:value})} value={this.state.Taskname}/>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Description:</Text>
          <TextInput underlineColorAndroid={'black'} style={styles.input}
          onChangeText={(value) => this.setState({Taskdescription:value})} value={this.state.Taskdescription}/>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Deadline:</Text>
          <TouchableHighlight style={styles.content} onPress={()=>{this.changeDate()}}>
          <Text style={styles.input}>{this.state.ddate.getMonth()+'/'+this.state.ddate.getDate()+'/'+
          this.state.ddate.getFullYear()}</Text>
        </TouchableHighlight>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Employer:</Text>
          <Picker selectedValue={this.state.name} style={styles.input}
           onValueChange={(itemValue, itemIndex) => this.setState({idp: itemIndex,name:itemValue})}>
            {this.renderPickerItems(this.state.names)}
          </Picker>
        </View>
        <TouchableHighlight style={styles.cont} onPress={()=>{this.onSubmit()}}>
        <View style={styles.button}>
          <Text style={{fontSize:20}}>Submit</Text>
        </View>
      </TouchableHighlight>
      </KeyboardAvoidingView>
    );
  }
}


const styles = StyleSheet.create({
  wrapper: {
    borderRadius: 20,
     backgroundColor: '#eee',
     flex:1
   },
   formContainner:{
     alignSelf:'stretch',
     backgroundColor: '#eee',
     flex:0.21

   },
   text:{
    textAlign:'left',
    fontWeight :'bold',
    minWidth : 50,
    fontSize:18,
    backgroundColor: '#eee',
    flex:0.5
   },
    content:{
      flex:0.5,
      backgroundColor: '#eee',
    },
  button:
  {
    backgroundColor: '#eee',
    width: 150, 
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
    backgroundColor: '#eee',
    flex:0.7
  }
});
*/