(ns restql.server.response-util)

(defn get-max-age-header [headers]
    (get headers "max-age"))

(defn calculate-max-age [query-response]
    (let [max-ages (->> query-response
                        (vals)
                        (map :details)
                        (flatten)
                        (map :headers)
                        (map get-max-age-header))]
    (if (some nil? max-ages)
        0
        (apply min (map #(. Integer parseInt %) max-ages)))))