import React from 'react';
import {ScrollView,Dimensions} from 'react-native';
import { VictoryBar, VictoryChart, VictoryAxis,VictoryTheme } from 'victory-native';
import {StackNavigator} from 'react-navigation';
import {DaBe} from './Database';

let winSize = Dimensions.get('window');

export class Chart extends React.Component {
    static navigationOptions={
        title:'Tasker'
      }
    render() {
      const {navigate}=this.props.navigation;
      var listVal=new Array();
      var listForm=new Array();
      var data=new Array();

      var listp=DaBe.getPersons();
      for(var i=0;i<listp.length;i++)
      {
          listVal.push(i+1);
          listForm.push(listp[i].name);
          data.push({pers:i+1,nr:DaBe.getNrOfTasks(listp[i].id)});
      }

      return (
        <ScrollView style={{flex:1,alignItems:'center'}}>
        <VictoryChart
          theme={VictoryTheme.material}
          domainPadding={20}
        >
        <VictoryAxis
          tickValues={listVal}
          tickFormat={listForm}
        />
        <VictoryAxis
          dependentAxis
          tickFormat={(x) => (`${x}`)}
        />
          <VictoryBar
            data={data}
            x="pers"
            y="nr"
          />
        </VictoryChart>
        </ScrollView>
      )
    }
  }