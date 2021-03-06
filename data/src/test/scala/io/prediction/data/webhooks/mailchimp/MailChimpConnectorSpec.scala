/** Copyright 2015 TappingStone, Inc.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

package io.prediction.data.webhooks.mailchimp

import io.prediction.data.webhooks.ConnectorTestUtil

import org.specs2.mutable._

class MailChimpConnectorSpec extends Specification with ConnectorTestUtil {

  // TOOD: test other events
  // TODO: test different optional fields

  "MailChimpConnector" should {

    "convert subscribe to event JSON" in {

      val subscribe = Map(
        "type" -> "subscribe",
        "fired_at" -> "2009-03-26 21:35:57",
        "data[id]" -> "8a25ff1d98",
        "data[list_id]" -> "a6b5da1054",
        "data[email]" -> "api@mailchimp.com",
        "data[email_type]" -> "html",
        "data[merges][EMAIL]" -> "api@mailchimp.com",
        "data[merges][FNAME]" -> "MailChimp",
        "data[merges][LNAME]" -> "API",
        "data[merges][INTERESTS]" -> "Group1,Group2",
        "data[ip_opt]" -> "10.20.10.30",
        "data[ip_signup]" -> "10.20.10.30"
      )

      val expected = """
        {
          "event" : "subscribe",
          "entityType" : "user",
          "entityId" : "8a25ff1d98",
          "targetEntityType" : "list",
          "targetEntityId" : "a6b5da1054",
          "properties" : {
            "email" : "api@mailchimp.com",
            "email_type" : "html",
            "merges" : {
              "EMAIL" : "api@mailchimp.com",
              "FNAME" : "MailChimp",
              "LNAME" : "API"
              "INTERESTS" : "Group1,Group2"
            },
            "ip_opt" : "10.20.10.30",
            "ip_signup" : "10.20.10.30"
          },
          "eventTime" : "2009-03-26T21:35:57.000Z"
        }
      """

      check(MailChimpConnector, subscribe, expected)
    }

  }
}
