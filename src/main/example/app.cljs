(ns example.app
  (:require
   ["expo" :as ex]
   ["react-native" :as rn]
   ["react" :as react]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [shadow.expo :as expo]
   [example.events]
   [example.subs]
   ["react-navigation" :refer [createAppContainer]]
   ["react-navigation-stack" :as ReactNavigationStack]))

(def styles
  ^js (-> {:container
           {:flex 1
            :backgroundColor "#fff"
            :alignItems "center"
            :justifyContent "center"}
           :label
           {:fontWeight "normal"
            :fontSize 15
            :color "blue"}}
          (clj->js)
          (rn/StyleSheet.create)))

(defn app []
  [:> rn/View {:style (.-container styles)}
   [:> rn/Text {:style (.-label styles)} "Home page"]])

(def routes {:Home {:screen (r/reactify-component app)}})

(defonce nav-stack
  (createAppContainer (ReactNavigationStack/createStackNavigator
   (clj->js routes)
   #js {:initialRouteName "Home"})))

(defn app-root []
  [:> nav-stack {}])

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (rn/AppRegistry.registerComponent "main" #(identity nav-stack)))

