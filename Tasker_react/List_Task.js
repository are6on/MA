import React from 'react';
import { StyleSheet, Text, View, TouchableHighlight, FlatList ,Image,Alert} from 'react-native';
import Task from './Task';
import Taskarray from './Taskarray';
import {StackNavigator} from 'react-navigation';
import {DaBe} from './Database';

export class List_task extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  constructor(props) {
    super(props)
    console.log('----------------LIST_TASK--------------------:geting data');
    this.state = {
      data:DaBe.getTasks()
    }
    console.log('----------------List_Task--------------------:data:'+this.state.data);
    this.remove = this.remove.bind(this);
  }
  remove(item)
  {
    let that = this;
    console.log('----------------LIST_TASK--------------------:entering remove');
    Alert.alert(
      'Alert',
      'Do you want to remove task?',
      [
        {text: 'No', onPress: () => {return}},
        {text: 'Yes', onPress: () => {
          console.log('----------------LIST_TASK--------------------:deleting data');
          DaBe. removeTask(item.idt);
          console.log('----------------LIST_TASK--------------------:deleted data');
          console.log('----------------LIST_TASK--------------------:seting data');
          that.setState({data:DaBe.getTasks()});
          console.log('----------------LIST_TASK--------------------:data:'+this.state.data);
        }},
      ],
      { cancelable: false }
    );
  }
  
  render() {
    const {navigate}=this.props.navigation;
    return (
      <View style={styles.container}>
      <TouchableHighlight style={{height:42,backgroundColor:'grey',alignItems:'center'}}
      onPress={()=>navigate('CHART')}>
        <Text style={{fontSize: 20,color:'white'}}>Task per employer</Text>
      </TouchableHighlight>
        <FlatList
          style={styles.list}
          data={this.state.data}
          renderItem={({item}) =>
          <View>
          <View style={styles.row}>
            <TouchableHighlight style={styles.itemt} 
            onPress={()=>navigate('DePTask',{o:item})}> 
              <Text style={styles.item}>{item.name}</Text>
            </TouchableHighlight>
            <TouchableHighlight style={styles.wrapper} onPress={()=>navigate('EditTask',{o:item})}>
              <Image source={require('/falcut/an3/an3sem1/MA/Ma/Tasker_react/img/add.png')} 
              style={styles.image}/>
            </TouchableHighlight>
            <TouchableHighlight style={styles.wrapper} onPress={() =>this.remove(item)}>
              <Image source={require('/falcut/an3/an3sem1/MA/Ma/Tasker_react/img/del.png')} 
              style={styles.image}/>
            </TouchableHighlight>
          </View>
          <View style={{height: 1,width: "86%",backgroundColor: "#4c69a5",marginLeft: "14%"}}/>
          </View>

        }
          keyExtractor={item => item.idt}
          renderSeparator={ () => {
            return (
             <View style={{height: 1,width: "86%",backgroundColor: "#CED0CE",marginLeft: "14%"}}/>
            );}}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  image: {
    flex: 1,
    width: 44,
    height: 44,
    resizeMode: 'contain'
},
  container: {
   flex: 1,
   backgroundColor: '#4c69a5',
   
  },
  row:{
    flexDirection:'row',
    backgroundColor: '#ffffff',
    alignItems:'center'

  },
  itemt: {
    padding: 10,
    height: 44,
    flex:0.6,
    alignItems:'flex-start'
  },
  item: {
    fontSize: 18,
    padding: 10,
    height: 44,
    marginBottom:10,
    
  },
  list:{
    flex:1,

  },
  wrapper: {
    borderRadius: 20,
    height: 44,
    alignItems: 'center',
    justifyContent: 'center',
    flex:0.2

  },
})
