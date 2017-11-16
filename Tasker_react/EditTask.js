import React from 'react';
import { StyleSheet, View, TouchableHighlight, Text, 
  KeyboardAvoidingView, TextInput, TouchableOpacity,
  DatePickerAndroid,Picker} from 'react-native';
import { NavigationActions } from 'react-navigation';
import Task from './Task';
import Taskarray from './Taskarray';

export class Edit_task extends React.Component {
    static navigationOptions={
        title:'Tasker'
      }
      constructor(props) {
        super(props)
        var nam=['All','Tom','Ron'];
        var obj=this.props.navigation.state.params.o;
        this.state = {
          object:obj,
          ddate:obj.getDeadline(),
          Taskname:obj.getTaskName(),
          Taskdescription:obj.getTaskDescription(),
          idp:obj.getIdP(),
          name:nam[obj.getIdP()-1],
          idt:obj.getIdT(),
          idm:0
        }
      }
      onSubmit(){
          this.state.object.name=this.state.Taskname;
          this.state.object.description=this.state.Taskdescription;
          this.state.object.idp=this.state.idp;
          this.state.object.deadline=this.state.ddate;
          const resetAction = NavigationActions.reset({
            index: 1,
            actions:[ {routeName:'Home'}, {routeName:'ListTask'} ]
          });
      
          this.props.navigation.dispatch(resetAction);
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