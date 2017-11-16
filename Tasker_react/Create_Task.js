import React from 'react';
import { StyleSheet, View, TouchableHighlight, Text, 
  KeyboardAvoidingView, TextInput, TouchableOpacity,
  DatePickerAndroid,Picker,Linking, AsyncStorage } from 'react-native';
import Task from './Task';
import Taskarray from './Taskarray';

export class Create_task extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  constructor(props) {
    super(props)
    this.state = {
      ddate:new Date(2017,1,1),
      Taskname: '',
      Taskdescription:'',
      idp:0,
      name:'All',
      idm:0,
      idt:Taskarray.getInstance().list.length
    }


  }
  newId(){
    var i=this.state.idt;
    this.setState({idt:i+1});
    return i;
  }
  onSubmit(){
    Taskarray.getInstance().list.push(new Task(this.state.Taskname,this.state.Taskdescription
                          ,this.newId(),this.state.idm,this.state.idp,this.state.ddate));
    msg='Hello there you have a new task:\n'+this.state.Taskname+
      ':\n'+this.state.Taskdescription+'\nDeadline:'+
      this.state.ddate.getMonth()+'/'+this.state.ddate.getDate()+'/'+
      this.state.ddate.getFullYear();
    Linking.openURL('mailto:somethingemail@gmail.com?subject=New Task&body='+msg);
  }

  async changeDate(){
    try { 
      const {action, year, month, day} = await DatePickerAndroid.open({ 
        date: new Date(), minDate:new Date() }); 
        if (action !== DatePickerAndroid.dismissedAction) {
          this.setState({ddate:new Date(year,month,day)});
        } 
      } catch ({code, message}) { 
        console.warn('Cannot open date picker', message); }
  }

  render() {
    const {navigate}=this.props.navigation;
    return (
      <KeyboardAvoidingView behavior='padding' style={styles.wrapper}>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Task Name:</Text>
          <TextInput underlineColorAndroid={'black'} 
          onChangeText={(value) => this.setState({Taskname:value})} value={this.state.Taskname}/>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Description:</Text>
          <TextInput underlineColorAndroid={'black'}
          onChangeText={(value) => this.setState({Taskdescription:value})} value={this.state.Taskdescription}/>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Deadline:</Text>
          <TouchableHighlight style={styles.content} onPress={()=>{this.changeDate()}}>
          <Text>{this.state.ddate.getMonth()+'/'+this.state.ddate.getDate()+'/'+
          this.state.ddate.getFullYear()}</Text>
        </TouchableHighlight>
        </View>
        <View style={styles.formContainner}>
          <Text style={styles.text}>Employer:</Text>
          <Picker selectedValue={this.state.name}
           onValueChange={(itemValue, itemIndex) => this.setState({idp: itemIndex+1,name:itemValue})}>
            <Picker.Item label="All" value="All" />
            <Picker.Item label="Tom" value="Tom" />
            <Picker.Item label="Ron" value="Ron" />
</Picker>
        </View>
        <TouchableHighlight style={styles.wrapper} onPress={()=>{this.onSubmit()}}>
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
     alignItems: 'center',
     justifyContent: 'center',
     backgroundColor: '#eee'
   },
   formContainner:{
     alignSelf:'stretch',
   },
   text:{

   },
    content:{

    }
});