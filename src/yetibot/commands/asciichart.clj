(ns yetibot.commands.asciichart
  (:require
    [clojure.string :refer [join]]
    [yetibot.util :refer [split-kvs]]
    [yetibot.hooks :refer [cmd-hook]]))

; TODO: smart scaling
(defn chartify [padding [label x]]
  (let [x (read-string (str x))
        label (str label)]
    (format "%4s: %s" label (join (repeat x "*")))))

(defn max-label [m] (apply max (map (fn [[k v]] (count (str k))) m)))

(defn asciichart-cmd
  [{items :opts}]
  "asciichart <collection> # outputs a simple ascii chart. <collection> may either be
   a list of numbers, or a map whose vals are numbers."
  ; normalize items into a nested vector to be chartified
  (let [parsed-map (or (split-kvs items) (map vector items items))
        chartify-padded (partial chartify (max-label items))]
    (map chartify-padded parsed-map)))

(cmd-hook #"asciichart"
          _ asciichart-cmd)