import React from 'react';
import { StyleSheet, Text, View, TouchableHighlight } from 'react-native';
import {StackNavigator} from 'react-navigation';
import {Create_task} from './Create_Task';
import {List_task} from './List_Task';
import {Edit_task} from './EditTask';
import {DaBe} from './Database';
import {DPTask} from './DPTask';
import {Chart} from './Chart';

export class Home extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  render() {
    const {navigate}=this.props.navigation;
    return (
      <View style={styles.container}>
      <View style={styles.cont}>
        <TouchableHighlight style={styles.wrapper} onPress={()=>navigate('CreateTask')}>
        <View style={styles.button}>
          <Text style={{fontSize:20}}>Create Task</Text>
        </View>
      </TouchableHighlight>
      </View>
      <View  style={styles.cont}>
      <TouchableHighlight style={styles.wrapper} onPress={()=>navigate('ListTask')}>
        <View style={styles.button}>
          <Text style={{fontSize:20}}>Tasks</Text>
        </View>
      </TouchableHighlight>
      </View>
      </View>
    );
  }
}

const NavigatorApp =StackNavigator({
  Home:{screen:Home},
  CreateTask:{screen:Create_task},
  ListTask:{screen:List_task},
  EditTask:{screen:Edit_task},
  DePTask:{screen:DPTask},
  CHART:{screen:Chart}
  },{navigationOptions:{
    headerStyle:{
    alignContent: 'center',
    backgroundColor: '#eee'
    }
  }
  });

export default class App extends React.Component{
  render(){
    return <NavigatorApp/>
  }
}



const styles = StyleSheet.create({
  cont:{
    flex:0.4,
    alignItems: 'center',
    justifyContent: 'center',
  },
  wrapper: {
     borderRadius: 5,
     alignItems: 'center',
     justifyContent: 'center',

   },
  container: {
    flex: 1,
    backgroundColor:'#55f',
    alignItems: 'center',
    justifyContent: 'center',
  },
  button: { backgroundColor: '#eee',
   width: 150, 
   padding: 10, 
   alignItems:'center',
   justifyContent:'center',
   borderRadius: 20,
  },
});

