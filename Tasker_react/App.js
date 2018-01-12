import React from 'react';
import { StyleSheet, Text, View, TouchableHighlight } from 'react-native';
import {StackNavigator} from 'react-navigation';
import {Create_task} from './Create_Task';
import {List_task} from './List_Task';
import {Edit_task} from './EditTask';
import {DPTask} from './DPTask';
import {Chart} from './Chart';
import {Taskarray} from './Taskarray';
import {Login} from './Login';
import {ref,firebaseAuth} from './Firebasedb';


export class Home extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  constructor(props){
    super(props);
    this.authlis=null;
    this.state={
      auth:null,
      loading:true
    }
  }
  componentDidMount(){
    const {navigate}=this.props.navigation;
    this.authlis=firebaseAuth.onAuthStateChanged((user) => {
      console.log("---------------------APP-------------------:no user");
    if(!user)
      navigate('LOGIN');
    else{
      console.log("---------------------APP-------------------:user");
      var pers=ref.child('Person');
      pers.once('value').then((snapshot)=>{
        var perso=snapshot.val();
        for(var e in perso)
          if(e.address==user.email){
            TaskArray.getInstance().p=e;
            console.log("---------------------APP-------------------:"+e.name);
          }
        setState({loading:false,auth:user});
      });
    }
    });
  }

  componentWillUnmount(){
    this.authlis();
  }

  render() {
    if(this.state.loading)
    return(<View>loading...</View>);
    const {navigate}=this.props.navigation;
    if(Taskarray.getInstance().p.role==0)
    return (
      <View style={styles.container}>
      <View style={styles.cont}>
        <TouchableHighlight style={styles.wrapper} onPress={()=>navigate('LOGIN')}>
        <View style={styles.button}>
          <Text style={{fontSize:20}}>Log in</Text>
        </View>
      </TouchableHighlight>
      </View>
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
    else
    return (
      <View style={styles.container}>
      <View style={styles.cont}>
        <TouchableHighlight style={styles.wrapper} onPress={()=>navigate('LOGIN')}>
        <View style={styles.button}>
          <Text style={{fontSize:20}}>Log in</Text>
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
  CHART:{screen:Chart},
  LOGIN:{screen:Login}
  },{navigationOptions:{
    headerStyle:{
    alignContent: 'center',
    backgroundColor: '#eee'
    }
  }
  });

export default class App extends React.Component{
  componentDidMount(){
  }
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

