import React from 'react';
import { StyleSheet, View, TouchableHighlight, Text, ScrollView,
  KeyboardAvoidingView, TextInput, TouchableOpacity,Dimensions ,
  DatePickerAndroid,Picker} from 'react-native';
import { NavigationActions } from 'react-navigation';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view'
import Task from './Task';
import {ref,firebaseAuth} from './Firebasedb';

export class Edit_task extends React.Component {
    static navigationOptions={
        title:'Tasker'
      }
      constructor(props) {
        super(props)
        var obj=this.props.navigation.state.params.o;
        this.state = {
          object:obj,
          ddate:obj.deadline,
          Taskname:obj.name,
          Taskdescription:obj.description,
          persons:null,
          idp:this.posp(obj.idp,list),
          name:'',
          idt:obj.idt,
          idm:obj.idm,
          names:null,
          loading:true
        }
        this.pers=null;
        this.task=null;
      }
      componentDidMount(){
        that=this;
        this.pers=ref.ref('Persons').orderByChild('role').equalTo(1);
        this.pers.on('value',(dataSnapshoot)=>{
          if(dataSnapshoot.exists()){
            var list=dataSnapshoot.val();
            var namelist=new Array();
            namelist.push('All');
            for(var i=0;i<list.length;i++)
              namelist.push(list[i].name);
            that.setState({persons:list,names:namelist,idp:that.posp(that.state.idt,list)});
          }
        });
        this.task=ref.ref('Tasks').child(this.state.idt);
        this.task.on('value',(dataSnapshoot)=>{
          var t=dataSnapshoot.val();
          that.setState({ddate:t.deadline,
            Taskname:t.name,
            Taskdescription:t.description,idp:that.posp(t.idp,list),
            name:that.state.names[that.posp(t.idp,list)-1],loading:false,
            idm:obj.idm,});
        })
      }

      posp(id,l)
      {
        console.log('----------------EDIT_TASK--------------------:posp list:'+l);
        for(var i=0;i<l.length;i++)
          if(l[i].id==id)
            return i;
        return 0;
      }

      onSubmit(){
        console.log('----------------EDIT_TASK--------------------:updating');
        var id=this.state.idp;
        if(id!=0){
          ref.ref("Tasks").child(idt).update(new Task(this.state.Taskname,this.state.Taskdescription
                ,this.state.idt,idt,this.state.idm,this.state.persons[id-1].id,this.state.ddate));
          }
        else{
          console.log('----------------EDIT_TASK--------------------:all selected');
          ref.ref("Tasks").child(idt).update(new Task(this.state.Taskname,this.state.Taskdescription
            ,this.state.idt,this.state.idm,this.state.persons[id-1].id,this.state.ddate));
          for(var i=0;i<this.persons.length;i++)
            if(i!=id-1){
              var idt =ref.child('Tasks').push().key;
              ref.ref("Tasks").child(idt).set(new Task(this.state.Taskname,this.state.Taskdescription
                    ,idt,this.state.idm,this.state.persons[i].id,this.state.ddate));
            }
        }
        console.log('----------------EDIT_TASK--------------------:reseting');
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
              this.setState({ddate:month+1+'/'+day+'/'+year});
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
          <Text style={styles.input}>{this.state.ddate}</Text>
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