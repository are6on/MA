import React from 'react';
import { StyleSheet, Text, View, TouchableHighlight, FlatList } from 'react-native';
import Task from './Task';
import Taskarray from './Taskarray';
import {StackNavigator} from 'react-navigation';

export class List_task extends React.Component {
  static navigationOptions={
    title:'Tasker'
  }
  constructor(props) {
    super(props)
    this.state = {
      data:Taskarray.getInstance().list
    }
  }
  
  render() {
    const {navigate}=this.props.navigation;
    return (
      <View style={styles.container}>
        <FlatList
          style={styles.list}
          data={this.state.data}
          renderItem={({item}) =>
          <TouchableHighlight style={styles.itemt} 
          onPress={()=>navigate('EditTask',{o:item})}
         > 
          <Text style={styles.item}>{item.name}</Text>
          </TouchableHighlight>
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
  container: {
   flex: 1,
   paddingTop: 22,
   backgroundColor: '#eee',
   
  },
  itemt: {
    padding: 10,
    height: 44,
    backgroundColor: '#eee',
    
  },
  item: {
    fontSize: 18,
    padding: 10,
    height: 44,
    backgroundColor: '#eee',
    
  },
  list:
  {
    flex:1,
    backgroundColor: '#eee',

  }
})
