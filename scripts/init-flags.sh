set -e

UNLEASH_URL=${UNLEASH_URL:-http://localhost:4242}
ADMIN_TOKEN=${ADMIN_TOKEN:?ADMIN_TOKEN is required}

create_flag () {
  NAME="$1"
  DESC="$2"

  echo "Creating flag: $NAME"

  curl -s -o /dev/null -w "%{http_code}" \
    -X POST "$UNLEASH_URL/api/admin/projects/default/features" \
    -H "Authorization: Bearer $ADMIN_TOKEN" \
    -H "Content-Type: application/json" \
    -d "{\"name\":\"$NAME\",\"description\":\"$DESC\",\"type\":\"release\"}" \
    | grep -E "^(200|201|409)$" > /dev/null
}

create_flag "premium-pricing" "10% discount for premium products"
create_flag "order-notifications" "Logs order confirmation notification"
create_flag "bulk-order-discount" "15% discount for quantity > 5"

echo "Flags created (or already existed)."
