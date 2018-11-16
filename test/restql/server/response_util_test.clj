(ns restql.server.response-util-test
    (:require [clojure.test :refer :all]
              [restql.server.response-util :refer :all]))

(deftest test-get-max-age-header
    (testing "if only one max-age, get-max-age-header should return max-age string"
        (is (= 
            "50"
            (get-max-age-header {"max-age" "50"})))))